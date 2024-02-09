package com.missuo.server.controller.user;

import com.missuo.common.constant.MessageConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.exception.IllegalException;
import com.missuo.common.result.Result;
import com.missuo.pojo.entity.AddressBook;
import com.missuo.server.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Tag(name = "Address Book Management")
public class AddressBookController {

  @Autowired private AddressBookService addressBookService;

  @GetMapping("/list")
  @Operation(summary = "Get Address Book List")
  public Result list() {
    AddressBook addressBook = new AddressBook();
    addressBook.setUserId(BaseContext.getCurrentId());
    List<AddressBook> list = addressBookService.list(addressBook);
    return Result.success(list);
  }

  @PostMapping
  @Operation(summary = "Save Address")
  public Result save(@Validated @RequestBody AddressBook addressBook) {
    log.info("save address book: {}", addressBook);
    addressBookService.save(addressBook);
    return Result.success();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Address by ID")
  public Result getById(@PathVariable Long id) {
    log.info("get address book by id: {}", id);
    AddressBook addressBook = addressBookService.getById(id);
    return Result.success(addressBook);
  }

  @PutMapping
  @Operation(summary = "Update Address")
  public Result update(@Validated @RequestBody AddressBook addressBook) {
    if (addressBook.getId() == null) {
      throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
    }
    log.info("update address book: {}", addressBook);
    addressBookService.update(addressBook);
    return Result.success();
  }

  @PutMapping("/default")
  @Operation(summary = "Set Default Address")
  public Result setDefault(@RequestBody AddressBook addressBook) {
    if (addressBook.getId() == null) {
      throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
    }
    log.info("set default address book: {}", addressBook);
    addressBookService.setDefault(addressBook);
    return Result.success();
  }

  @DeleteMapping
  @Operation(summary = "Delete Address by ID")
  public Result deleteById(Long id) {
    log.info("delete address book by id: {}", id);
    addressBookService.deleteById(id);
    return Result.success();
  }

  @GetMapping("default")
  @Operation(summary = "Get Default Address")
  public Result getDefault() {
    AddressBook addressBook = new AddressBook();
    addressBook.setIsDefault(1);
    addressBook.setUserId(BaseContext.getCurrentId());
    List<AddressBook> list = addressBookService.list(addressBook);

    if (list.size() == 1) {
      return Result.success(list.getFirst());
    } else {
      return Result.error("No default address found");
    }
  }
}
