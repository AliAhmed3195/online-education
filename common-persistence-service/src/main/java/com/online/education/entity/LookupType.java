//package com.online.education.entity;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.List;
//
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "LOOKUP_TYPE")
//public class LookupType {
//
//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "description")
//    private String description;
//
////    @OneToMany(mappedBy = "lookupType", fetch = FetchType.LAZY)
////    @OrderBy("display_order asc")
////    private List<LookupValue> values;
//}
