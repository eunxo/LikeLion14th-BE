package com.project.likelion14thbe.domain.order.entity;

import com.project.likelion14thbe.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order  {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 값을 자동으로 생성
    @Column(name = "order_id")
    private long orderId;

    @CreatedDate
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private String status;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "orderitem")
    private List<Orderitem> orderitems;

}


