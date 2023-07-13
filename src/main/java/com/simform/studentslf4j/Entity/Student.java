package com.simform.studentslf4j.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

@Data
@NoArgsConstructor
@Entity       //JPA dependency
@Table(name = "student")
//@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY , region = "student")
@Builder
public class Student {

  public Student(long id, String name, String technology) {
    this.id = id;
    this.name = name;
    this.technology = technology;
  }

  @Id
  @Column(name = "student_id")
  long id;
  @Column(name = "student_name")
  String name;
  @Column(name = "student_technology")
  String technology;


}
