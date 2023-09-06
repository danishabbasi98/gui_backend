package com.vodacom.er.gui.service;


import com.vodacom.er.gui.entity.Functions;
import com.vodacom.er.gui.entity.Groups;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.mapper.RequestEsGroupFunction;
import com.vodacom.er.gui.mapper.UpdateRequestEsGroupFunction;
import com.vodacom.er.gui.repository.EsFunctionsRepository;
import com.vodacom.er.gui.repository.EsGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EsGroupService {
    @Autowired
    private EsGroupRepository esGroupRepository;

    @Autowired
    private EsFunctionsRepository esFunctionsRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public Groups saveGroup(Groups groups) {
        logger.info("EsGroupService : saveGroup() Method Called : Successfully ");
        return esGroupRepository.save(groups);
    }

    public List<Groups> allGroup() {
        logger.info("EsGroupService : allGroup() Method Called : Successfully ");
        return esGroupRepository.findAll();
    }

    public Optional<Groups> findGroupById(Integer id) {
        logger.info("EsGroupService : findGroupById() Method Called : Successfully ");
        return esGroupRepository.findById(id);
    }

    public Groups findByGroupName(String name) {
        logger.info("EsGroupService : findByGroupName() Method Called : Successfully ");
        return esGroupRepository.findByGroupName(name);
    }

    public Optional<Groups> getActualGroupById(Integer id) {
        logger.info("EsGroupService : getActualGroupById() Method Called : Successfully ");
        return esGroupRepository.findById(id);
    }

    public Optional<Groups> updateEsGroup(Groups _groups) {
        logger.info("EsGroupService : updateEsGroup() Method Called :  ");
        Optional<Groups> stu = esGroupRepository.findById(_groups.getSeq());
        Groups s = stu.get();
        s.setSeq(_groups.getSeq());
//      s.setEs_group_functions(_groups.getEs_group_functions());
        s.setName(_groups.getName());
        s.setDescription(_groups.getDescription());
        s.setActive_yn(_groups.getActive_yn());
        s.setModified_date(_groups.getModified_date());
        s.setModified_by(_groups.getModified_by());
        esGroupRepository.save(s);
        logger.info("EsGroupService : updateEsGroup() Method Called : Successfully ");
        return esGroupRepository.findById(_groups.getSeq());
    }

    public Optional<Groups> createEsGroup(Groups _groups) {
        logger.info("EsGroupService : createEsGroup() Method Called :  ");
        Groups s = new Groups();
        s.setSeq(_groups.getSeq());
//      s.setEs_group_functions(_groups.getEs_group_functions());
        s.setName(_groups.getName());
        s.setDescription(_groups.getDescription());
        s.setActive_yn(_groups.getActive_yn());
        s.setModified_date(_groups.getModified_date());
        s.setModified_by(_groups.getModified_by());
        esGroupRepository.save(s);
        logger.info("EsGroupService : createEsGroup() Method Called : Successfully ");
        return esGroupRepository.findById(_groups.getSeq());
    }

    public Groups createEsGroupFunction(RequestEsGroupFunction es_groups, Functions functions) {
        logger.info("EsGroupService : createEsGroupFunction() Method Called :  ");
        Groups s = new Groups();
//        s.setSeq(es_groups.getSeq());
//        s.setEs_group_functions(functions.getSeq());
        s.setName(es_groups.getName());
        s.setDescription(es_groups.getDescription());
        s.setActive_yn(es_groups.getActive_yn());
        s.setModified_date(es_groups.getModified_date());
        s.setModified_by(es_groups.getModified_by());
        Groups _groups1 = esGroupRepository.save(s);
        if (_groups1.getSeq() != 0) {
//            Groups _groups1 = requestToActualGroupConvert(es_groups);
            Set<Groups> setGroup = new HashSet<>();
            Set<Functions> setFunction = new HashSet<>();
            setFunction.add(functions);
            setGroup.add(_groups1);
            functions.setES_GROUP_FUNCTIONS(setGroup);
//            s.setEs_group_functions();
//            s.setEs_group_functions(setFunction);
//            _groups1.setEs_group_functions(setFunction);
            Groups _groups2 = esGroupRepository.save(s);
//            esFunctionsRepository.save(functions);
            logger.info("EsGroupService : createEsGroupFunction() Method Called : Successfully ");
            return _groups2;
        }
        logger.info("EsGroupService : createEsGroupFunction() Method Called : Successfully ");
        return esGroupRepository.findByGroupName(_groups1.getName());
    }

    public Groups createEsGroupFunctionForList(UpdateRequestEsGroupFunction es_groups) {
        logger.info("EsGroupService : createEsGroupFunctionForList() Method Called :  ");
        Groups s = new Groups();
//        s.setSeq(es_groups.getSeq());
//        s.setEs_group_functions(functions.getSeq());
        s.setName(es_groups.getName());
        s.setDescription(es_groups.getDescription());
        s.setActive_yn(es_groups.getActive_yn());
        s.setModified_date(es_groups.getModified_date());
        s.setModified_by(es_groups.getModified_by());
        Groups _groups1 = esGroupRepository.save(s);
        logger.info("EsGroupService : createEsGroupFunctionForList() Method Called : Successfully ");
        return esGroupRepository.findByGroupName(_groups1.getName());
    }

//    public void deleteEsGroupById(Integer id) {
//        esGroupRepository.deleteById(id);
//    }
    public Optional<Groups> deleteEsGroupById(Integer id) {
        logger.info("EsGroupService : deleteEsGroupById() Method Called :  ");
        Optional<Groups> delGroup = esGroupRepository.findById(id);

        if(delGroup.isPresent()){
            logger.info("EsGroupService : deleteEsGroupById() Method Called : Successfully ");
            esGroupRepository.deleteById(id);
            return delGroup;
        }
        logger.info("EsGroupService : deleteEsGroupById() Method Called : Invalid Id ");
        return null;
    }
}
