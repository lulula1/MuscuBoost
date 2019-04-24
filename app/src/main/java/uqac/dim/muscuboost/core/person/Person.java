package uqac.dim.muscuboost.core.person;

import java.util.Calendar;
import java.util.Date;

// TODO - Comment code
public class Person {

    private String name;
    private String surname;
    private Date date_naissance;

    public Person(String name, String surname, Date date_naissance){
        this.name = name;
        this.surname = surname;
        this.date_naissance = date_naissance;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public int calculAge(){
        Calendar curr = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(date_naissance);
        int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        curr.add(Calendar.YEAR,-yeardiff);
        if(birth.after(curr))
        {
            yeardiff = yeardiff - 1;
        }
        return yeardiff;
    }

}
