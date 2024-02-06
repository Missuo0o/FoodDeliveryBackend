package com.missuo.server.mapper;

import com.missuo.pojo.entity.AddressBook;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AddressBookMapper {

  List<AddressBook> list(AddressBook addressBook);

  @Insert(
      "insert into address_book"
          + "        (user_id, consignee, phone, sex,"
          + "         detail, label, is_default)"
          + "        values (#{userId}, #{consignee}, #{phone}, #{sex},"
          + "        #{detail}, #{label}, #{isDefault})")
  void insert(AddressBook addressBook);

  @Select("select * from address_book where id = #{id}")
  AddressBook getById(Long id);

  void update(AddressBook addressBook);

  @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
  void updateIsDefaultByUserId(AddressBook addressBook);

  @Delete("delete from address_book where id = #{id}")
  void deleteById(Long id);
}
