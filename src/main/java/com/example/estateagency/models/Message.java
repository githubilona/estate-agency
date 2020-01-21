package com.example.estateagency.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;


@Setter
@Getter
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 2, max = 100000)
    private String messageText;


    @Column(name="created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    //@NotBlank
    private String email;

    @JoinColumn(name="property_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Property property;

    @JoinColumn(name="userSender_id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userSender;

    @JoinColumn(name="userReceiver_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userReceiver;


    @JoinColumn(name="conversation_id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Conversation conversation;

    public Message() {
        this.creationDate = new Date();
    }

}
