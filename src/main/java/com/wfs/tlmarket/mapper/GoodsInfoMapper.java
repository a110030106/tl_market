package com.wfs.tlmarket.mapper;


import com.wfs.tlmarket.models.GoodsInfo;

import java.util.List;

public interface GoodsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Integer id);

    GoodsInfo selectByGoodsNo(String goodsNo);
    List<GoodsInfo> selectByGoodsName(String goodsName);
    List<GoodsInfo> selectByGoodsType(int goodsType);
    List<GoodsInfo> selectByIsHot(int isHot);
    List<GoodsInfo> selectByIsNew(int isNew);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);
}