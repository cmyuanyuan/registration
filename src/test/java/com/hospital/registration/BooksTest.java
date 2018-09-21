package com.hospital.registration;

import com.hospital.registration.domain.Books;
import com.hospital.registration.mapper.BooksMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 挂号业务测试类
 *
 * @author SONG
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksTest {
  @Autowired
  private BooksMapper booksMapper;

  @Test
  public void find(){
    for(Books books:booksMapper.findAllBook(1,1,"430121199910010101")){
      System.out.println(books.getPname());
    }
  }
}
