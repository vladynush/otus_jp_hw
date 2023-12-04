package ru.otus.javapro.homeworks.hw12caching.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address implements Cloneable {

    @Id
    @SequenceGenerator(name = "address_gen", sequenceName = "common_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    public Address clone() {
        try {
            Address newAddress = (Address) super.clone();
            newAddress.setStreet(this.getStreet());
            return newAddress;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
