package com.vodacom.er.gui.service;


import com.vodacom.er.gui.entity.Functions;
import com.vodacom.er.gui.entity.Group_Functions;
import com.vodacom.er.gui.entity.Groups;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.mapper.RequestEsGroupFunction;
import com.vodacom.er.gui.mapper.UpdateRequestEsGroupFunction;
import com.vodacom.er.gui.repository.EsFunctionsRepository;
import com.vodacom.er.gui.repository.EsGroupFunctionsRepository;
import com.vodacom.er.gui.repository.EsGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EsGroupFunctionService {

    @Autowired
    private EsGroupFunctionsRepository esGroupFunctionsRepository;
    @Autowired
    private EsFunctionsRepository esFunctionsRepository;
    @Autowired
    private EsGroupRepository esGroupRepository;
    @Autowired
    private EsGroupService esGroupService;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public Group_Functions saveGroupFunction(Group_Functions groups) {
        logger.info("EsGroupFunctionService : saveGroupFunction() Method Called : Successfully ");
        return esGroupFunctionsRepository.save(groups);
    }

    public List<Group_Functions> allGroupFunction() {
        logger.info("EsGroupFunctionService : allGroupFunction() Method Called : Successfully ");
        return esGroupFunctionsRepository.findAll();
    }

    public Optional<Group_Functions> findGroupFunctionById(Integer id) {
        logger.info("EsGroupFunctionService : findGroupFunctionById() Method Called : Successfully ");
        return esGroupFunctionsRepository.findById(id);
    }

    public Optional<Group_Functions> getActualGroupFunctionById(Integer id) {
        logger.info("EsGroupFunctionService : getActualGroupFunctionById() Method Called : Successfully ");
        return esGroupFunctionsRepository.findById(id);
    }

    public List<Functions> getActualUserGroupWithFunctionsSeqId(RequestEsGroupFunction requestEsGroupFunction, Integer group_id) {
        logger.info("EsGroupFunctionService : getActualUserGroupWithFunctionsSeqId() Method Called :  ");
        List<Group_Functions> findGroupFunction = esGroupFunctionsRepository.findAll();
        List<Group_Functions> findUserGroupFunctionAllow = findGroupFunction.stream()
                .filter(function -> function.getGroup_seq() == group_id && function.getFunction_seq() == requestEsGroupFunction.getFunction_seq())
                .collect(Collectors.toList());
        List<Functions> allFunctions = esFunctionsRepository.findAll();

        List<Functions> allAllowGroupf = new ArrayList<>();
        for (int i = 0; i < findUserGroupFunctionAllow.size(); i++) {
            final int function_seq = findUserGroupFunctionAllow.get(i).getFunction_seq();

            List<Functions> allAllowGroup = allFunctions.stream()
                    .filter(function -> function.getSeq() == requestEsGroupFunction.getFunction_seq())
                    .collect(Collectors.toList());
            if (allAllowGroup.size() != 0) {
                allAllowGroupf.addAll(allAllowGroup);
            }
        }
        logger.info("EsGroupFunctionService : getActualUserGroupWithFunctionsSeqId() Method Called : Successfully ");
        return allAllowGroupf;
    }

    public Groups createEsGroupFunction(RequestEsGroupFunction es_groups) {
        logger.info("EsGroupFunctionService : createEsGroupFunction() Method Called :  ");
        Optional<Functions> findFunction = esFunctionsRepository.findById(es_groups.getFunction_seq());
        if (!findFunction.isPresent()) {
            logger.info("EsGroupFunctionService : createEsGroupFunction() Method Called : No function relationship found ");
            return null;
        }
        Groups findGroup = esGroupRepository.findByGroupName(es_groups.getName());

        if (findGroup != null) {
            logger.info("EsGroupFunctionService : createEsGroupFunction() Method Called : Group already exist ");
            return null;
        }
        if (findFunction.isPresent()) {

            Group_Functions s = new Group_Functions();
            Groups _groups1 = esGroupService.createEsGroupFunction(es_groups, findFunction.get());

            s.setFunction_seq(findFunction.get().getSeq());
//            s.setGroup_seq(_groups1.getSeq());
            s.setGroup_seq(_groups1.getGroup_seq());
            s.setModified_date(_groups1.getModified_date());
            s.setModified_by(_groups1.getModified_by());
            esGroupFunctionsRepository.save(s);
            logger.info("EsGroupFunctionService : createEsGroupFunction() Method Called : Successfully ");
            return _groups1;
        } else {
            logger.info("EsGroupFunctionService : createEsGroupFunction() Method Called : Not Created ");
            return null;
        }
    }

    public Groups createEsGroupFunctionList(UpdateRequestEsGroupFunction es_groups) {
        logger.info("EsGroupFunctionService : createEsGroupFunctionList() Method Called :  ");

        List<Functions> findFunction = es_groups.getEs_functions();
        Groups findGroup = esGroupRepository.findByGroupName(es_groups.getName());
        if (findGroup != null) {
            logger.info("EsGroupFunctionService : createEsGroupFunctionList() Method Called : Group already exist ");
            return null;
        }
        if (findFunction != null) {

            Groups _groups1 = esGroupService.createEsGroupFunctionForList(es_groups);

            for (int i = 0; i < findFunction.size(); i++) {
                Group_Functions s = new Group_Functions();
//                for function valid check
                Optional<Functions> esFunctions = esFunctionsRepository.findById(findFunction.get(i).getSeq());
                if (esFunctions.isPresent()) {
                    s.setFunction_seq(esFunctions.get().getSeq());
                    s.setGroup_seq(_groups1.getGroup_seq());
                    s.setModified_date(_groups1.getModified_date());
                    s.setModified_by(_groups1.getModified_by());
                    esGroupFunctionsRepository.save(s);

                }else {
                    continue;
                }

            }
            logger.info("EsGroupFunctionService : createEsGroupFunctionList() Method Called : Successfully ");
            return _groups1;
        } else {
            logger.info("EsGroupFunctionService : createEsGroupFunctionList() Method Called : Not created ");
            return null;
        }
    }
//    public void  deleteEsGroupById(Integer id) {
//        esGroupFunctionsRepository.deleteById(id);
//    }
}
