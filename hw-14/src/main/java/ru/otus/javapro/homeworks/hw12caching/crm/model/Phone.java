package ru.otus.javapro.homeworks.hw12caching.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phones")
public class Phone implements Cloneable{

    @SequenceGenerator(name = "phone_gen", sequenceName = "common_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public Phone clone() {
        try {
            Phone newPhone = (Phone) super.clone();
            newPhone.setNumber(this.number);
            newPhone.setClient(this.client);
            return newPhone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
