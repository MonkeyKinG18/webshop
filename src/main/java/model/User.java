package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "info")
public class Info {

    @Id
    @Column(name = "user_id")
    @NotNull
    private String userId;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "name")
    @NotNull
    private String name;




}