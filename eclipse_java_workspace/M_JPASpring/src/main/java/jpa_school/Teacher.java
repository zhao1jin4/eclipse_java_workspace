package jpa_school;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name="JPA_TEACHER")
public class Teacher implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
    @OneToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH} ,
    			fetch=FetchType.LAZY,mappedBy="teacher",targetEntity=Student.class)//有mappedBy是主方
    private Set<Student> students;
    
    @Version
    private int ver;
    
    private String name;
    
	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public Teacher(int id, String name, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public Teacher() {
    }

    public Teacher(int id, Set<Student> students) {
        this.id = id;
        this.students = students;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", students=" + students + ", ver=" + ver + ", name=" + name + "]";
	}

}
