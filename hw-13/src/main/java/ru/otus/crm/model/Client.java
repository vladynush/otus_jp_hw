package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "client")
    private List<Phone> phoneList;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phoneList) {
        this.phoneList = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.address = address;
        if (phoneList != null) {
            for (Phone phone : phoneList
            ) {
                this.phoneList.add(new Phone(phone.getId(), phone.getNumber(), this));
            }
        }
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phoneList);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phoneList + '\'' +
                '}';
    }
}
