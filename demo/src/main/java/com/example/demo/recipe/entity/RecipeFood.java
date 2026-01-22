//package com.example.demo.recipe.entity;
//
//import com.example.demo.food.entity.Food;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
//@Table(name = "recipe_foods")
//public class RecipeFood {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
////    수량, 용량 ex) 3, 200, 2,..
//    private int quantity;
//
////    단위 ex) 개, ml, 큰술,..
//    private String unit;
//
//    @ManyToOne
//    @JoinColumn(name = "food_id", referencedColumnName = "id")
//    Food food;
//
//    @ManyToOne
//    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
//    Recipe recipe;
//}
