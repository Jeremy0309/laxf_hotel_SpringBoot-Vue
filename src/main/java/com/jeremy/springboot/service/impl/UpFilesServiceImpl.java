package com.jeremy.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeremy.springboot.entity.UpFiles;
import com.jeremy.springboot.mapper.UpFilesMapper;
import com.jeremy.springboot.service.IUpFilesService;
import org.springframework.stereotype.Service;

@Service
public class UpFilesServiceImpl extends ServiceImpl<UpFilesMapper, UpFiles> implements IUpFilesService {
}
