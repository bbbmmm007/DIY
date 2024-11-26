package com.zhny.computer.mapper;

import com.zhny.computer.entity.Product;
import com.zhny.computer.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProductMapper {
    // 根据商品ID查询商品信息
    Product findById(@Param("id") Integer id);
    //删除商品
    Integer deleteProduct(Integer id);
    //添加商品
    Integer insertProduct(Product product);
    // 由商品ID获取到所有分类ID
    List<Product> showAllProductId(@Param("id") Integer id);
    //统计商品数量
    Integer countProducts(@Param("params") ProductVO params);
    // 查询商品信息，根据条件和分页
    List<Product> findProducts(@Param("params") ProductVO params,
                               @Param("pageSize") Integer pageSize,
                               @Param("offset") Integer offset);
    Product showProduct(@Param("id") Integer id);
    //更新商品信息
    Integer updateProduct(
            @Param("itemType") String itemType,
            @Param("price") Long price,
            @Param("image") String image,
            @Param("salt") Integer salt,
            @Param("performanceScore") Integer performanceScore,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime,
            @Param("id") Integer id);
    //更新商品收藏次数
    Integer updateProductCollectCount(@Param("id") Integer id);
    //更新商品加入配置车次数
    Integer updateProductCartCount(@Param("id") Integer id);







   //选择cpu
    Product autoSelectBestValueD4IntelCPU( @Param("minCPUBudget") Integer minCPUBudget, @Param("maxCPUBudget") Integer maxCPUBudget,@Param("priceOrder") String priceOrder);
    Product autoSelectBestValueD4D5IntelCPU( @Param("minCPUBudget") Integer minCPUBudget, @Param("maxCPUBudget") Integer maxCPUBudget,@Param("priceOrder") String priceOrder);
    Product autoSelectBestValueD5AmdCPU( @Param("minCPUBudget") Integer minCPUBudget, @Param("maxCPUBudget") Integer maxCPUBudget,@Param("priceOrder") String priceOrder);
    Product autoSelectBestValueD4AmdCPU(@Param("minCPUBudget") Integer minCPUBudget, @Param("maxCPUBudget") Integer maxCPUBudget,@Param("priceOrder") String priceOrder);
    //选择显卡
    Product autoSelectBestValue16GPU( @Param("parentId") Integer parentId,@Param("childId") Integer childId,@Param("minGPUBudget") Integer minGPUBudget, @Param("maxGPUBudget") Integer maxGPUBudget,@Param("priceOrder") String priceOrder);
    Product autoSelectBestValue12GPU( @Param("parentId") Integer parentId,@Param("childId") Integer childId,@Param("minGPUBudget") Integer minGPUBudget, @Param("maxGPUBudget") Integer maxGPUBudget,@Param("priceOrder") String priceOrder);
    Product autoSelectBestValue8GPU( @Param("parentId") Integer parentId,@Param("childId") Integer childId,@Param("minGPUBudget") Integer minGPUBudget, @Param("maxGPUBudget") Integer maxGPUBudget,@Param("priceOrder") String priceOrder);
    Product autoSelectBestValue4GPU( @Param("parentId") Integer parentId,@Param("childId") Integer childId,@Param("minGPUBudget") Integer minGPUBudget, @Param("maxGPUBudget") Integer maxGPUBudget,@Param("priceOrder") String priceOrder);
    //散热选择
    Product autoSelectBestValueCooler(@Param("parentId") Integer parentId,@Param("childId") Integer childId, @Param("minCoolingBudget") Integer minCoolingBudget,@Param("maxCoolingBudget") Integer maxCoolingBudget, @Param("order") String order);
    //cpu选择
    Product autoSelectBestValueIntel(@Param("parentId") Integer parentId,@Param("childId") Integer childId, @Param("minMotherboardBudget") Integer minMotherboardBudget, @Param("maxMotherboardBudget") Integer maxMotherboardBudget,@Param("order") String order);
    Product autoSelectBestValueAmd(@Param("parentId") Integer parentId,@Param("childId") Integer childId, @Param("minMotherboardBudget") Integer minMotherboardBudget, @Param("maxMotherboardBudget") Integer maxMotherboardBudget,@Param("order") String order);
    //机箱选择
    Product autoSelectBestValueCase(@Param("parentId") Integer parentId,@Param("childId") Integer childId, @Param("minCaseBudget") Integer minCaseBudget, @Param("maxCaseBudget") Integer maxCaseBudget, @Param("priceOrder") String priceOrder);
    //电源选择
    Product autoSelectBestValueSUE(@Param("supplySize") Integer supplySize,@Param("parentId") Integer parentId,@Param("childId") Integer childId, @Param("minSUEBudget") Integer minSUEBudget, @Param("maxSUEBudget") Integer maxSUEBudget, @Param("priceOrder") String priceOrder);
    //固态选择
    Product autoSelectBestValueSSD(@Param("parentId") Integer parentId,@Param("ssdSize") String ssdSize, @Param("minSsdBudget") Integer minSsdBudget,@Param("maxSsdBudget") Integer maxSsdBudget, @Param("order") String order);
    //内存选择
    Product autoSelectBestValueMemory(@Param("parentId") Integer parentId,@Param("childId") Integer childId,@Param("memorySize") Integer memorySize, @Param("minMemoryBudget") Integer minMemoryBudget,@Param("maxMemoryBudget") Integer maxMemoryBudget, @Param("order") String order);
}

