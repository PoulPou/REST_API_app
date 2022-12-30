package com.example.restapiapp.service;

import com.example.restapiapp.exception.departmentException.NullDepartmentException;
import com.example.restapiapp.exception.personException.*;
import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.DepartmentRepository;
import com.example.restapiapp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository pr;
    private final DepartmentRepository dr;

    public PersonService(PersonRepository pr, DepartmentRepository dr) {
        this.pr = pr;
        this.dr = dr;
    }

    //  сздание персоны
    public void createPerson(Person person) throws Exception {
        Calendar startDate = new GregorianCalendar(1960, 0, 1);
        if (person.getDepartment() == null) {
            throw new NullDepartmentException(); // Департамент нe может быть равен null ++
        } else if(!person.getName().matches("[\\u002D\\u0401\\u0451\\u0410-\\u044f]+")){
            throw new NameException(); // Проверка имени   [-\p{IsCyrillic}]
        } else if(!person.getSurname().matches("[\\u002D\\u0401\\u0451\\u0410-\\u044f]+")){
            throw new NameException();  // Проверка фамилии
        } else if(person.getPatronymic() != null && !person.getPatronymic().matches("[\\u002D\\u0401\\u0451\\u0410-\\u044f]+")){
            throw new NameException();  // Проверка отчества(если есть)
        }else if (person.getDateOfDismissal() != null) {
            throw new NotNullDateDismissalException(); // Заполнена дата увольнения ++
        } else if (person.getBirthday().after(person.getDateOfEmployment())) {
            throw new BirthdayDateException(); // Дата рождения после даты трудоустройства ++
        } else if (person.getBirthday().before(startDate.getTime())){
            throw new BirthdayDateException(); // Дата рождения не может быть раньше 1960г.(62 года)
        } else if (person.getPhoneNumber().matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}") == false) {
            throw new IncorrectPhoneNumberException(); // Телефооный номер должен содержать только символы "+-0123456789() ++
        } else if (person.getSalary() > 500000) {
            throw new IncorrectSalaryException(); // Зарплата более 500000 р. ++
        } else if (pr.countPersonByDepartment(person.getDepartment()) > 0) {
            Department d = dr.findById(person.getDepartment().getId()).orElseThrow(NullDepartmentException::new);
            if (person.isSupervisor() == true && pr.findPersonBySupervisorAndDepartment_Name(true, d.getName()).getId()
                    > 0) {
                throw new SupervisorParameterExclusion(); // Два начальника в департаменте ++
            }
            if (pr.findPersonBySupervisorAndDepartment_Name(true, d.getName()).getSalary() <=
                    person.getSalary()) {
                throw new IncorrectSalaryException(); // Зарплата выше чем у начальника ++
            }
        }
     else {
        pr.save(person);
    }
}

    // все персоны
    public List<Person> findAllPerson() {
        return pr.findAll();
    }

    // редактирование персоны
    public void updatePerson(Person person) throws Exception {
        Calendar startDate = new GregorianCalendar(1960, 0, 1);
        if (person.getDepartment() == null) {
            throw new NullDepartmentException(); //Департамент нe может быть равен null ++
        } else if(!person.getName().matches("[\\u002D\\u0401\\u0451\\u0410-\\u044f]+")){
            throw new NameException(); //Проверка имени  ++
        } else if(!person.getSurname().matches("[\\u002D\\u0401\\u0451\\u0410-\\u044f]+")){
            throw new NameException();  //Проверка фамилии  ++
        } else if(person.getPatronymic() != null && !person.getPatronymic().matches("[\\u002D\\u0401\\u0451\\u0410-\\u044f]+")){
            throw new NameException();  //Проверка отчества(если есть)  ++
        }else if (person.getDateOfDismissal() != null) {
            throw new NotNullDateDismissalException(); //Заполнена дата увольнения ++
        } else if (person.getBirthday().after(person.getDateOfEmployment())) {
            throw new BirthdayDateException(); //Дата рождения после даты трудоустройства ++
        } else if (person.getBirthday().before(startDate.getTime())){
            throw new BirthdayDateException(); //Дата рождения не может быть раньше 1960г.(62 года)++
        } else if (person.getPhoneNumber().matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}") == false) {
            throw new IncorrectPhoneNumberException(); //Телефооный номер должен содержать только символы "+-0123456789() ++
        } else if (person.getSalary() > 500000) {
            throw new IncorrectSalaryException(); //Зарплата более 500000 р. ++
        } else if (pr.countPersonByDepartment(person.getDepartment()) > 0) {
            Department d = dr.findById(person.getDepartment().getId()).orElseThrow(NullDepartmentException::new);
            if (person.isSupervisor() == true && pr.findPersonBySupervisorAndDepartment_Name(true, d.getName()).getId()
                    != person.getId()) {
                throw new SupervisorParameterExclusion(); //Два начальника в департаменте ++
            }
            if (pr.findPersonBySupervisorAndDepartment_Name(true, d.getName()).getId() != person.getId() &&
                    pr.findPersonBySupervisorAndDepartment_Name(true, d.getName()).getSalary() <=+ person.getSalary()) {
                throw new IncorrectSalaryException(); //Зарплата выше чем у начальника ++
            }
        }
        else {
            pr.save(person);
        }
    }

    // удаление персоны
    public void deletePerson(Long id, Date dateDelete) throws Exception {
        Person person = pr.findById(id).orElseThrow(NullPersonException::new);
        if (person.getDateOfEmployment().after(dateDelete)) {
            throw new DateDeleteException();
        } else if (person.isSupervisor() == true) {
            Person maxDateOfEmploymentPerson = pr.mostSeniorEmployee(person.getDepartment().getId());
            maxDateOfEmploymentPerson.setSupervisor(true);
            pr.delete(person);
            pr.save(maxDateOfEmploymentPerson);
        } else {
            pr.delete(person);
        }
    }

    // получение персоны по Id
    public Person getById(Long id) throws NullPersonException {
        return pr.findById(id).orElseThrow(NullPersonException::new);
    }

    // получить босса этого человека
    public Person getBossThisPerson(Long id) throws NullPersonException {
        Person person = pr.findById(id).orElseThrow(NullPersonException::new);
        return pr.findPersonBySupervisorAndDepartment_Name(true, person.getDepartment().getName());
    }

    // перевести персону в другой департамент
    public void transferPerson(Long idPerson, Long idDepartment) throws Exception {
        Person person = pr.findById(idPerson).orElseThrow(NullPersonException::new);
        if (person.isSupervisor() == true) {
            Person maxDateOfEmploymentPerson = pr.mostSeniorEmployee(person.getDepartment().getId());
            maxDateOfEmploymentPerson.setSupervisor(true);
            person.setDepartment(dr.findById(idDepartment).orElseThrow(NullDepartmentException::new));
            person.setSupervisor(false);
            pr.save(person);
            pr.save(maxDateOfEmploymentPerson);
        } else {
            person.setDepartment(dr.findById(idDepartment).orElseThrow(NullDepartmentException::new));
            pr.save(person);
        }

    }
}
