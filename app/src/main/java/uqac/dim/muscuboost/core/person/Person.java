package uqac.dim.muscuboost.core.person;

import java.util.Calendar;
import java.util.Date;

import uqac.dim.muscuboost.core.Identifiable;

// TODO - Comment code
public class Person implements Identifiable {

    private String name;
    private String surname;
    private Date birthDate;

    public Person(String name, String surname, Date birthDate){
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    @Override
    public long getId() {
        return -1;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int computeAge(){
        Calendar curr = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);
        int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        curr.add(Calendar.YEAR,-yeardiff);
        if(birth.after(curr))
        {
            yeardiff = yeardiff - 1;
        }
        return yeardiff;
    }

}
