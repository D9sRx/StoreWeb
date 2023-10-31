package com.example.springboot.mapper;

import com.example.springboot.pojo.Goods.Goods;
import com.example.springboot.pojo.Goods.GoodsDetailImage;
import com.example.springboot.pojo.Goods.Sku;
import com.example.springboot.pojo.Goods.SkuDetail;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailInfo;
import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.Goods.mvc.SkuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

/* 对goods表进行操作 */
    //添加商品
    public int insertGoods(Goods goods);

    //修改商品
    public int updateGoods(Goods goods);

    //删除商品（因为绑定了外键，所以一删除上层的Godos，下层的也会跟着删除）
    public int delGoods(int goodsId);

    //查询某个分类的所有商品信息（单一查找，还需要嵌套sku数据）
    public List<GoodsInfo> selectGoodsByCategoryId(int categoryId);

    //查询某个商品的详情信息（根据storeId 以及goodsId ）
    public GoodsDetailInfo selectGoodsDetailInfoByStoreIdWithGoodsId(int storeId, int goodsId);

    //查询某个商品的信息（根据storeId 以及goodsId ）
    public GoodsInfo selectGoodsInfoByStoreIdWithGoodsId(int storeId, int goodsId);

    public Goods selectGoodsByStoreIdWithGoodsId(int storeId, int goodsId);

    //根据商品名称模糊查询是·商品基础信息
    public List<Goods> selectGoodsByGoodsName(String goodsName);

    //查询某商家的所有商品基础信息
    public List<Goods> selectGoodsByStoreId(int storeId);

    public List<GoodsInfo> selectGoodsInfoByStoreId(int storeId, String goodsName);


/* 对sku表的操作 */
    //添加商品规格
    public int insertSku(Sku sku);

    //修改商品规格
    public int updateSku(Sku sku);

    //删除商品规格（根据商品id删除）
    public int delSku(int storeId, int goodsId);

    //查询商品规格（单一查找，还需要嵌套skuInfo数据）（分商家）
    public List<SkuInfo> selectSkuInfoByStoreIdWithGoodsId(int storeId, int goodsId);

    //查询商品规格信息
    public SkuInfo selectSkuInfoByStoreIdWithSkuId(int storeId, int skuId);

/* 对skuDetail表的操作 */
    //添加规格配件
    public int insertSkuDetail(SkuDetail skuDetail);

    //修改规格配件
    public int updateSkuDetail(SkuDetail skuDetail);

    //删除规格配件
    public int delSkuDetail(int skuId);

    //查询商品配件（分商家）
    public List<SkuDetail> selectSkuDetailBySkuId(int storeId, int skuId);


/* 对商品详情页照片的处理*/
    //添加图片
    public int insertGoodsDetailImage(GoodsDetailImage goodsDetailImage);

    //删除图片


    //查询某商品的所有详情图片（type 为（1: swiper 2: static））
    public List<GoodsDetailImage> selectGoodsDetailImageByGoodsIdWithType(int storeId, int goodsId, int type);




/* 多表查询单个商品的价格 */

    public double selectOneGoodsPrice(int storeId,int goodsId, int skuId);


    public int selectStoreIdByGoodsId(int goodsId);

    List<GoodsInfo> selectGoodsInfoByStoreIdWithBlurName(int storeId,String goodsName);
}
