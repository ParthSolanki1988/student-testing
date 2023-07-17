package com.simform.studentslf4j.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simform.studentslf4j.Entity.Student;
import com.simform.studentslf4j.Service.Impl.StudentServiceImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

  Student student;
  Student student1;
  Student student3;
  List<Student> studentList = new ArrayList<>();
  List<Student> studentArrayList = new ArrayList<>();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private StudentServiceImplementation studentServiceImplementation;

  @BeforeEach
  void setUp() {
    student = Student.builder().id(1010L).name("MihirRaj").technology("dotNet").build();

    student1 = Student.builder().id(1290L).name("ParthRaj").technology("Java").build();

    studentList.add(student);
    studentList.add(student1);
  }

  /*save student test cases*/
  @Nested
  class saveStudent{
    @Test
    void givenStudentObject_whenSaveStudent_thenReturnStudentObject_positive() throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      String requestJson = objectMapper.writeValueAsString(student);

      when(studentServiceImplementation.createStudent(student)).thenReturn(student);

      MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(requestJson))
              .andDo(print())
              .andExpect(status().isCreated()).andReturn();

      String contentAsString = result.getResponse().getContentAsString();
      assertThat(contentAsString).isEqualTo(requestJson);
    }

    @Test
    void givenStudentObject_whenSaveStudent_thenReturnStudentObject_negative() throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      String requestJson = objectMapper.writeValueAsString(student);

      when(studentServiceImplementation.createStudent(student3)).thenReturn(student3);

      MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students"))
              .andDo(print())
              .andExpect(status().is4xxClientError()).andReturn();
    }
  }
  /*End save student test cases*/


  /*Get All student test cases*/
 @Nested
 class GetAllStudent{
    @Test
    void givenStudentList_whenGetAllStudent_returnStudentList_positive() throws Exception {
      when(studentServiceImplementation.getAllStudents()).thenReturn(studentList);
      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students"))
              .andDo(print())
              .andExpect(status().isFound());
    }

    @Test
    void givenStudentList_whenGetAllStudent_returnStudentList_negative() throws Exception {
      when(studentServiceImplementation.getAllStudents()).thenReturn(studentArrayList);
      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students"))
              .andDo(print())
              .andExpect(status().isNotFound());
    }
  }
  /*End Get All student test cases*/


  /* Get student By Id Test cases*/

 @Nested
 class GetStudentById{
   @Test
   void givenStudentId_whenGetStudentById_thenReturnStudentObject_positive() throws Exception {

     //given
     when(studentServiceImplementation.findById(student.getId())).thenReturn(student);

     mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/1010"))
             .andDo(print())
             .andExpect(status().isFound());
   }

   @Test
   void givenStudentId_whenGetStudentById_thenReturnStudentObject_negative() throws Exception {
     when(studentServiceImplementation.findById(student.getId())).thenReturn(student);
     mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/101"))
             .andDo(print())
             .andExpect(status().isNotFound());
   }
 }
  /* End Get student By Id Test cases*/


  @AfterEach
  void tearDown() {
    student = null;
    student1 = null;
    student3 = null;
    studentList = null;
  }
}
