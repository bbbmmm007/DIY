package com.zhny.computer.service.util;

import com.zhny.computer.entity.Product;
import com.zhny.computer.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSelect {

    @Autowired
    private ProductMapper productMapper;
    // 根据CPU类型和预算选择
    public Product selectCpu(String cpuType,int baseBudget, int minCPUBudget, int maxCPUBudget) {
        String orderPrice = "ASC";
        if (baseBudget<=(minCPUBudget+maxCPUBudget)/2){
            orderPrice = "DESC";
        }
        switch (cpuType) {
            case "IntelD4":
                return productMapper.autoSelectBestValueD4IntelCPU(minCPUBudget, maxCPUBudget, orderPrice);
            case "AmdD5":
                return productMapper.autoSelectBestValueD5AmdCPU(minCPUBudget, maxCPUBudget, orderPrice);
            case "IntelD4D5":
                return productMapper.autoSelectBestValueD4D5IntelCPU(minCPUBudget, maxCPUBudget, orderPrice);
            case "AmdD4":
                return productMapper.autoSelectBestValueD4AmdCPU(minCPUBudget, maxCPUBudget, orderPrice);
            default:
                throw new IllegalArgumentException("无效的 CPU 类型: " + cpuType);
        }
    }
    // 根据GPU类型和显存大小选择最佳 GPU
    public Product selectGpu(Integer gpuType, Integer gpuModel, Integer gpuSize,int baseBudget, int minGPUBudget, int maxGPUBudget) {
        // 验证 GPU 类型
        if (gpuType == null || (gpuType != 21 && gpuType != 22)) {
            throw new IllegalArgumentException("无效的 GPU 类型: " + gpuType);
        }
        // 确定价格排序
        String priceOrder = (baseBudget <=(minGPUBudget+maxGPUBudget)/2) ? "DESC" : "ASC";

        // 根据 GPU 大小选择最佳产品
        switch (gpuSize) {
            case 4:
                return productMapper.autoSelectBestValue4GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            case 8:
                return productMapper.autoSelectBestValue8GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            case 12:
                return productMapper.autoSelectBestValue12GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            case 16:
                return productMapper.autoSelectBestValue16GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            default:
                throw new IllegalArgumentException("无效的 GPU 大小: " + gpuSize);
        }
    }
    // 根据主板类型和预算选择最佳主板
    public Product selectMotherboard(Integer motherboardType, Integer motherboardVersion, Integer motherboardModel,int baseBudget, int minMotherboardBudget, int maxMotherboardBudget) {


        String order = "ASC";
        if(baseBudget <= (minMotherboardBudget+maxMotherboardBudget)/2) {
            order = "DESC";
        }
        // 定义要调用的方法
        Product selectedProduct = null;

        // 判断主板类型（1 = Intel, 2 = AMD）
        if (motherboardType == 1 || motherboardType == 2) {
            // Intel 和 AMD 共用的版本和模型判断逻辑
            switch (motherboardVersion) {
                case 41:
                case 42:
                    switch (motherboardModel) {
                        case 411:
                        case 412:
                        case 413:
                        case 421:
                        case 422:
                        case 423:
                            // 根据类型调用不同的方法
                            if (motherboardType == 1) {
                                selectedProduct = productMapper.autoSelectBestValueIntel(
                                        motherboardVersion, motherboardModel, minMotherboardBudget, maxMotherboardBudget, order
                                );
                            } else if (motherboardType == 2) {
                                selectedProduct = productMapper.autoSelectBestValueAmd(
                                        motherboardVersion, motherboardModel, minMotherboardBudget, maxMotherboardBudget, order
                                );
                            }
                            break;
                        default:
                            // 处理无效模型的情况
                            throw new IllegalArgumentException("Invalid motherboard model: " + motherboardModel);
                    }
                    break;
                default:
                    // 处理无效版本的情况
                    throw new IllegalArgumentException("Invalid motherboard version: " + motherboardVersion);
            }
        } else {
            // 处理无效类型的情况
            throw new IllegalArgumentException("Invalid motherboard type: " + motherboardType);
        }

        return selectedProduct;
    }
    // 根据内存类型和大小选择最佳内存
    public Product selectMemory(Integer memoryType, Integer memoryModel, Integer memorySize,int baseBudget, int minMemoryBudget, int maxMemoryBudget) {
        String order = "ASC";
        if(baseBudget<=(minMemoryBudget+maxMemoryBudget)/2){
            order = "DESC";
        }
        switch (memoryType) {
            case 61:
                switch (memoryModel){
                    case 611:
                        switch (memorySize) {
                            case 8:
                            case 16:
                            case 32:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                    case 612:
                        switch (memorySize) {
                            case 8:
                            case 16:
                            case 32:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                }

            case 62:
                switch (memoryModel){
                    case 621:
                        switch (memorySize) {
                            case 16:
                            case 24:
                            case 32:
                            case 64:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                    case 622:
                        switch (memorySize) {
                            case 16:
                            case 24:
                            case 32:
                            case 64:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                }


            default:
                throw new IllegalArgumentException("无效的内存类型: " + memoryType);
        }
    }
    public Product selectSsd(Integer ssdType, String ssdSize,int baseBudget, int minSsdBudget, int maxSsdBudget) {
        // 确定排序方式
        String order = "ASC";
        if(baseBudget<=(minSsdBudget+maxSsdBudget)/2){
            order = "DESC";
        }
        // 确保 parentId 是有效的
        if (ssdType != 71 && ssdType != 72) {
            throw new IllegalArgumentException("无效的 Parent ID: " + ssdType);
        }
        // 处理 SSD 容量
        switch (ssdSize) {
            case "215G":
            case "512G":
            case "1T":
            case "2T":
            case "4T":
                return productMapper.autoSelectBestValueSSD(ssdType, ssdSize, minSsdBudget, maxSsdBudget, order);
            default:
                throw new IllegalArgumentException("无效的 SSD 容量: " + ssdSize);
        }
    }
    // 根据散热类型和预算选择最佳散热器
    public Product selectCooler(Integer coolingType, Integer coolingSize,int baseBudget, int minCoolingBudget, int maxCoolingBudget) {
        String order = "ASC";

        if (baseBudget <= (minCoolingBudget + maxCoolingBudget) / 2){
            order = "DESC";
        }
        switch (coolingType) {
            case 31:
                switch (coolingSize) {
                    case 311:
                    case 312:
                        return productMapper.autoSelectBestValueCooler(coolingType,coolingSize,minCoolingBudget, maxCoolingBudget,order);
                    default:
                        break;
                }
                break;
            case 32:
                switch (coolingSize) {
                    case 321:
                    case 322:
                    case 323:
                        return productMapper.autoSelectBestValueCooler(coolingType,coolingSize,minCoolingBudget, maxCoolingBudget,order);
                    default:
                        break;
                }
                break;
            default:
                throw new IllegalArgumentException("无效的散热类型: " + coolingType);
        }
        return null;
    }
    // 选择电源
    public Product selectSupplyE(Integer supplySize, Integer supplyType, Integer supplyModel,int baseBudget, int minSUEBudget, int maxSUEBudget) {
        String priceOrder = "ASC";
        if (baseBudget<=(minSUEBudget+maxSUEBudget)/2) {
            priceOrder = "DESC";
        }

        switch (supplyType) {
            case 51:
                switch (supplyModel) {
                    case 511:
                    case 512:
                        return productMapper.autoSelectBestValueSUE(supplySize,supplyType, supplyModel, minSUEBudget, maxSUEBudget, priceOrder);
                    default:
                        break;
                }
                break;

            case 52:
                switch (supplyModel) {
                    case 521:
                    case 522:
                        return productMapper.autoSelectBestValueSUE(supplySize,supplyType, supplyModel, minSUEBudget, maxSUEBudget, priceOrder);
                    default:
                        break;
                }
                break;

            default:
                break;
        }

        // 如果没有符合的返回情况，返回 null 或根据逻辑返回合适的默认值
        return null;
    }
    // 选择机箱
    public Product selectCase(Integer caseType, Integer caseModel,Integer baseBudget, int minCaseBudget, int maxCaseBudget) {
        String priceOrder = "ASC";  // 修正拼写
        if (baseBudget<=(minCaseBudget+maxCaseBudget)/2) {
            priceOrder = "DESC";
        }

        switch (caseType) {
            case 81:
                switch (caseModel) {
                    case 811:
                    case 812:
                        return productMapper.autoSelectBestValueCase( caseType,caseModel, minCaseBudget, maxCaseBudget, priceOrder);
                    default:
                        break;  // 添加 break，防止 fall-through
                }
                break;  // 添加 break，防止 fall-through

            case 82:
                switch (caseModel) {
                    case 821:
                    case 822:
                        return productMapper.autoSelectBestValueCase( caseType,caseModel, minCaseBudget, maxCaseBudget, priceOrder);
                    default:
                        break;  // 添加 break
                }
                break;  // 添加 break

            default:
                break;  // 添加默认情况
        }

        // 如果没有符合的条件，返回 null 或其他适当的默认值
        return null;
    }
    // 根据CPU类型和预算选择
    public Product selectCheapCpu(String cpuType) {
        int minCPUBudget =0;
        int maxCPUBudget =20000;
        String orderPrice = "ASC";
        switch (cpuType) {
            case "IntelD4":
                return productMapper.autoSelectBestValueD4IntelCPU(minCPUBudget, maxCPUBudget, orderPrice);
            case "AmdD5":
                return productMapper.autoSelectBestValueD5AmdCPU(minCPUBudget, maxCPUBudget, orderPrice);
            case "IntelD4D5":
                return productMapper.autoSelectBestValueD4D5IntelCPU(minCPUBudget, maxCPUBudget, orderPrice);
            case "AmdD4":
                return productMapper.autoSelectBestValueD4AmdCPU(minCPUBudget, maxCPUBudget,orderPrice);
            default:
                throw new IllegalArgumentException("无效的 CPU 类型: " + cpuType);
        }
    }
    // 根据 GPU 类型和显存大小选择最佳 GPU
    public Product selectCheapGpu(Integer gpuType, Integer gpuModel, Integer gpuSize) {
        // 验证 GPU 类型
        if (gpuType == null || (gpuType != 21 && gpuType != 22)) {
            throw new IllegalArgumentException("无效的 GPU 类型: " + gpuType);
        }
        int minGPUBudget = 0;
        int maxGPUBudget = 20000;
        // 确定价格排序
        String priceOrder = "ASC";

        // 根据 GPU 大小选择最佳产品
        switch (gpuSize) {
            case 4:
                return productMapper.autoSelectBestValue4GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            case 8:
                return productMapper.autoSelectBestValue8GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            case 12:
                return productMapper.autoSelectBestValue12GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            case 16:
                return productMapper.autoSelectBestValue16GPU(gpuType,gpuModel, minGPUBudget, maxGPUBudget, priceOrder);
            default:
                throw new IllegalArgumentException("无效的 GPU 大小: " + gpuSize);
        }
    }
    // 根据主板类型和预算选择最佳主板
    public Product selectCheapMotherboard(Integer motherboardType, Integer motherboardVersion, Integer motherboardModel) {
        int minMotherboardBudget = 0;
        int maxMotherboardBudget = 20000;
        String order = "ASC";

        // 定义要调用的方法
        Product selectedProduct = null;

        // 判断主板类型（1 = Intel, 2 = AMD）
        if (motherboardType == 1 || motherboardType == 2) {
            // Intel 和 AMD 共用的版本和模型判断逻辑
            switch (motherboardVersion) {
                case 41:
                case 42:
                    switch (motherboardModel) {
                        case 411:
                        case 412:
                        case 413:
                        case 421:
                        case 422:
                        case 423:
                            // 根据类型调用不同的方法
                            if (motherboardType == 1) {
                                selectedProduct = productMapper.autoSelectBestValueIntel(
                                        motherboardVersion, motherboardModel, minMotherboardBudget, maxMotherboardBudget, order
                                );
                            } else if (motherboardType == 2) {
                                selectedProduct = productMapper.autoSelectBestValueAmd(
                                        motherboardVersion, motherboardModel, minMotherboardBudget, maxMotherboardBudget, order
                                );
                            }
                            break;
                        default:
                            // 处理无效模型的情况
                            throw new IllegalArgumentException("Invalid motherboard model: " + motherboardModel);
                    }
                    break;
                default:
                    // 处理无效版本的情况
                    throw new IllegalArgumentException("Invalid motherboard version: " + motherboardVersion);
            }
        } else {
            // 处理无效类型的情况
            throw new IllegalArgumentException("Invalid motherboard type: " + motherboardType);
        }

        return selectedProduct;
    }
    // 根据内存类型和大小选择最佳内存
    public Product selectCheapMemory(Integer memoryType, Integer memoryModel, Integer memorySize) {
        int minMemoryBudget = 0;
        int maxMemoryBudget = 20000;
        String order="ASC";
        switch (memoryType) {
            case 61:
                switch (memoryModel){
                    case 611:
                        switch (memorySize) {
                            case 8:
                            case 16:
                            case 32:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                    case 612:
                        switch (memorySize) {
                            case 8:
                            case 16:
                            case 32:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                }

            case 62:
                switch (memoryModel){
                    case 621:
                        switch (memorySize) {
                            case 16:
                            case 24:
                            case 32:
                            case 64:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                    case 622:
                        switch (memorySize) {
                            case 16:
                            case 24:
                            case 32:
                            case 64:
                                return productMapper.autoSelectBestValueMemory(memoryType,memoryModel,memorySize,minMemoryBudget,maxMemoryBudget,order);
                        }
                }


            default:
                throw new IllegalArgumentException("无效的内存类型: " + memoryType);
        }
    }
    // 根据 SSD 容量和预算选择最佳 SSD
    public Product selectCheapSsd(Integer ssdType, String ssdSize) {
        // 确定排序方式
        int minSsdBudget = 0;
        int maxSsdBudget = 20000;
        String order = "ASC";


        // 确保 parentId 是有效的
        if (ssdType != 71 && ssdType != 72) {
            throw new IllegalArgumentException("无效的 Parent ID: " + ssdType);
        }
        // 处理 SSD 容量
        switch (ssdSize) {
            case "215G":
            case "512G":
            case "1T":
            case "2T":
            case "4T":
                return productMapper.autoSelectBestValueSSD(ssdType, ssdSize, minSsdBudget, maxSsdBudget, order);
            default:
                throw new IllegalArgumentException("无效的 SSD 容量: " + ssdSize);
        }
    }
    // 根据散热类型和预算选择最佳散热器
    public Product selectCheapCooler(Integer coolingType, Integer coolingSize) {
        String order = "ASC";
        int minCoolingBudget = 0;
        int maxCoolingBudget = 20000;

        switch (coolingType) {
            case 31:
                switch (coolingSize) {
                    case 311:
                    case 312:
                        return productMapper.autoSelectBestValueCooler(coolingType,coolingSize,minCoolingBudget, maxCoolingBudget,order);
                }
            case 32:
                switch (coolingSize) {
                    case 321:
                    case 322:
                    case 323:
                        return productMapper.autoSelectBestValueCooler(coolingType,coolingSize,minCoolingBudget, maxCoolingBudget,order);
                }
            default:
                throw new IllegalArgumentException("无效的散热类型: " + coolingType);
        }
    }
    // 选择电源
    public Product selectCheapSupplyE(Integer supplySize, Integer supplyType, Integer supplyModel) {
        String priceOrder = "ASC";
        int minSUEBudget = 0;
        int maxSUEBudget = 20000;
        return productMapper.autoSelectBestValueSUE(supplySize,supplyType,supplyModel,minSUEBudget, maxSUEBudget,priceOrder);
    }
    // 选择机箱
    public Product selectCheapCase(Integer CaseType, Integer CaseModel) {
        String priceOrder = "ASC";
        int minCaseBudget = 0;
        int maxCaseBudget = 20000;
        return productMapper.autoSelectBestValueCase(CaseType,CaseModel,minCaseBudget, maxCaseBudget,priceOrder);
    }
}
