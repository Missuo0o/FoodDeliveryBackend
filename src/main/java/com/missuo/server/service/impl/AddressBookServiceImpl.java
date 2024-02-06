package com.missuo.server.service.impl;

import com.missuo.common.context.BaseContext;
import com.missuo.pojo.entity.AddressBook;
import com.missuo.server.mapper.AddressBookMapper;
import com.missuo.server.service.AddressBookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressBookServiceImpl implements AddressBookService {
  @Autowired private AddressBookMapper addressBookMapper;

  public List<AddressBook> list(AddressBook addressBook) {
    return addressBookMapper.list(addressBook);
  }

  public void save(AddressBook addressBook) {
    addressBook.setUserId(BaseContext.getCurrentId());
    addressBook.setIsDefault(0);
    addressBookMapper.insert(addressBook);
  }

  public AddressBook getById(Long id) {
    return addressBookMapper.getById(id);
  }

  public void update(AddressBook addressBook) {
    addressBookMapper.update(addressBook);
  }

  @Transactional
  public void setDefault(AddressBook addressBook) {
    // Modify all addresses of the current user to non-default addresses
    addressBook.setIsDefault(0);
    addressBook.setUserId(BaseContext.getCurrentId());
    addressBookMapper.updateIsDefaultByUserId(addressBook);

    // Change current address to default address
    addressBook.setIsDefault(1);
    addressBookMapper.update(addressBook);
  }

  public void deleteById(Long id) {
    addressBookMapper.deleteById(id);
  }
}
