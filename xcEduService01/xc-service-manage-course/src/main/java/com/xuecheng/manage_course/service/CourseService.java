package com.xuecheng.manage_course.service;

import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanRepository teachplanRepository;

    @Autowired
    private CoursePicRepository coursePicRepository;


    public TeachplanNode findTeachplanList(String courseId) {

        return teachplanMapper.selectList(courseId);
    }

    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {

        //校验课程id和课程计划名称
        if (teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseid()) ||
                StringUtils.isEmpty(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.INVALIDPARAM);
        }

        //取出课程id
        String courseid = teachplan.getCourseid();
        //取出父结点id
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {
            //如果父结点为空则获取根结点
            parentid = getTeachplanRoot(courseid);
        }

        //取出父结点信息
        Optional<Teachplan> teachplanOptional = teachplanRepository.findById(parentid);
        if (!teachplanOptional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALIDPARAM);
        }

        //父结点
        Teachplan teachplanParent = teachplanOptional.get();
        //父结点级别
        String parentGrade = teachplanParent.getGrade();
        //设置父结点
        teachplan.setParentid(parentid);
        teachplan.setStatus("0");//未发布
        //子结点的级别，根据父结点来判断
        if (parentGrade.equals("1")) {
            teachplan.setGrade("2");
        } else if (parentGrade.equals("2")) {
            teachplan.setGrade("3");

        }
        //设置课程id
        teachplan.setCourseid(teachplanParent.getCourseid());
        teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private String getTeachplanRoot(String courseid) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if (!optional.isPresent()) {
            return null;
        }

        CourseBase courseBase = optional.get();

        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseid, "0");
        if (teachplanList == null || teachplanList.size() == 0) {
            //新增一个根结点
            Teachplan teachplanRoot = new Teachplan();
            teachplanRoot.setCourseid(courseid);
            teachplanRoot.setPname(courseBase.getName());
            teachplanRoot.setParentid("0");
            teachplanRoot.setGrade("1");//1级
            teachplanRoot.setStatus("0");//未发布
            teachplanRepository.save(teachplanRoot);
            return teachplanRoot.getId();
        }
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();

    }

    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {

        if (courseListRequest == null) {
            courseListRequest = new CourseListRequest();
        }

        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }

        PageHelper.startPage(page, size);
        List<CourseInfo> courseInfos = courseMapper.findAll();

        QueryResult<CourseInfo> queryResult = new QueryResult();
        queryResult.setList(courseInfos);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Transactional
    public AddCourseResult addCourseBase(CourseBase courseBase) {
        courseBaseRepository.save(courseBase);
        return new AddCourseResult(CommonCode.SUCCESS, courseBase.getId());
    }

    public CourseBase findCourseBaseById(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Transactional
    public ResponseResult updateCourseBase(String id, CourseBase courseBase) {
        Optional<CourseBase> optional = courseBaseRepository.findById(id);

        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CourseBase courseBase1 = optional.get();

        courseBase1.setName(courseBase.getName());
        courseBase1.setUsers(courseBase.getUsers());
        courseBase1.setMt(courseBase.getMt());
        courseBase1.setSt(courseBase.getSt());
        courseBase1.setGrade(courseBase.getGrade());
        courseBase1.setStudymodel(courseBase.getStudymodel());
        courseBase1.setDescription(courseBase.getDescription());

        courseBaseRepository.save(courseBase1);
        return ResponseResult.SUCCESS();
    }

    public CourseMarket getCourseMarketById(String courseId) {
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket) {

        CourseMarket courseMarket1 = this.getCourseMarketById(courseId);

        if (courseMarket1 == null) {
            courseMarket1 = new CourseMarket();
        }

        if (courseMarket == null) {
            ExceptionCast.cast(CommonCode.INVALIDPARAM);
        }

        courseMarket1.setId(courseId);
        courseMarket1.setCharge(courseMarket.getCharge());
        courseMarket1.setStartTime(courseMarket.getStartTime());
        courseMarket1.setEndTime(courseMarket.getEndTime());
        courseMarket1.setValid(courseMarket.getValid());
        courseMarket1.setQq(courseMarket.getQq());

        courseMarketRepository.save(courseMarket1);
        return ResponseResult.SUCCESS();

    }

    @Transactional
    public ResponseResult saveCoursePic(String courseId, String pic) {
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        CoursePic coursePic = null;
        if (picOptional.isPresent()) {
            coursePic = picOptional.get();
        }
        //没有课程图片则新建对象
        if (coursePic == null) {
            coursePic = new CoursePic();
        }
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        //保存课程图片
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CoursePic findCoursepic(String courseId) {
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    //删除课程图片
    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {
        //执行删除，返回1表示删除成功，返回0表示删除失败
        long result = coursePicRepository.deleteByCourseid(courseId);
        if(result>0){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}