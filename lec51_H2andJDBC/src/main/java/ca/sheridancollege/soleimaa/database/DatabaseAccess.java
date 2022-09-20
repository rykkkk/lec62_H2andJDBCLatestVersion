package ca.sheridancollege.soleimaa.database;

import ca.sheridancollege.soleimaa.beans.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public void insertStudent(String name){
        MapSqlParameterSource  namedParameters = new MapSqlParameterSource();
        String query ="INSERT INTO student (name) VALUES(:name1)";
        namedParameters.addValue("name1",name);
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0)
            System.out.print("\nStudent record was successfully inserted!");
    }

    public List<Student> getStudents() {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM student";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Student>(Student.class));
    }

    public void deleteStudentById(Long id){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM student WHERE id =:id1";
        namedParameters.addValue("id1",id);
        jdbc.update(query, namedParameters);
    }
//pass the student object instead of just the name or id
    public void updateStudentById(Student student){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE student SET name= :name WHERE id =:id"; //important . whatever appears after the : is the param name
        namedParameters.addValue("id", student.getId());
        namedParameters.addValue("name", student.getName()); //std.getname is the actual obj field but name is just the name
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0){
            System.out.println("Student record was successfully updated!");
        }
    }

    public List<Student> getStudent(Long id) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM student WHERE id=:id1";
        namedParameters.addValue("id1", id);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Student>(Student.class));
    }
}
