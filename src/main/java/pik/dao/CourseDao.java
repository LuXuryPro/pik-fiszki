package pik.dao;

import pik.dto.CourseInfo;

public interface CourseDao {

    public CourseInfo create(CourseInfo course);

    public CourseInfo update(CourseInfo course);

    public Boolean delete(CourseInfo course);

}
