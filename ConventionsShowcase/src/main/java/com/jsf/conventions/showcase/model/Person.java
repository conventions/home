package com.jsf.conventions.showcase.model;


import com.jsf.conventions.model.VersionatedBaseEntityLong;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * 
 * @author Rafael M. Pestano
 * @Date Jan 10, 2011
 */
@Entity
@Table(name = "person")
@SequenceGenerator(allocationSize = 1, name = "seq_person", sequenceName = "seq_person")
public class Person extends VersionatedBaseEntityLong implements Serializable{

    private static final long serialVersionUID = -3065107295253073402L;
    private String name;
    private String lastname;
    private Integer age;
    private List<Person> friends;

    public Person() {
    }

    public Person(String name, String lastname, Integer age) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }
    
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Size(min=6,max=100,message="Minimum size for name is 6 and maximum size is 100")
    @NotEmpty(message="The name is required")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min=6,max=100,message="Minimum size for lastname is 6 and maximum size is 100")
    @NotEmpty(message="The lastname is required")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String sobrenome) {
        this.lastname = sobrenome;
    }

    @ManyToMany
    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }
    
    @Transient
    public Integer getNumFriends(){
        if(friends != null){
            return friends.size();
        }
        else{
            return 0;
        }
    }

    
    /**
     * used as label in selectItem
     */
    @Override
    @Transient
    public String getLabel() {
        return this.getName();
    }
    
    /**
     * 
     * List 'contains' method does not work with proxy objects
     * 
     */
    public boolean hasFriend(Long friendId){
        for (Person person : friends) {
            if(person.getId().equals(friendId)){
                return true;
            }
        }
        return false;
    }
     
     public void removeFriend(Long friendId) {
       for (Iterator<Person> i = friends.iterator();i.hasNext();) {
           Person p = i.next();
            if(p.getId().equals(friendId)){
               i.remove();
            }
        }
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
         if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

}
