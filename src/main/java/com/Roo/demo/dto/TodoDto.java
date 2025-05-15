package com.Roo.demo.dto;

import com.Roo.demo.models.User;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TodoDto {

    int id;

    String todo;

    boolean done;

    Date deadline;
}
