package com.zhny.computer.service.util;

import com.zhny.computer.entity.Product;
import com.zhny.computer.vo.ProfileCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Service
public class ProfileGenerator {

    @Autowired
    private ProductSelect productSelect;

    @Autowired
    private BudgetPortion budgetPortion;

    public Map<String, Object> generateProfile(ProfileCreateVO request) {
        Map<String, Object> config = new HashMap<>();
        Integer totalBudget = request.getTotalBudget();

        // 初始保底配置
        Product cpuBase = productSelect.selectCheapCpu(request.getCpuType());
        Product gpuBase = productSelect.selectCheapGpu(request.getGpuType(), request.getGpuModel(), request.getGpuSize());
        Product motherboardBase = productSelect.selectCheapMotherboard(request.getMotherboardType(), request.getMotherboardVersion(), request.getMotherboardModel());
        Product memoryBase = productSelect.selectCheapMemory(request.getMemoryType(), request.getMemoryModel(), request.getMemorySize());
        Product ssdBase = productSelect.selectCheapSsd(request.getSsdType(), request.getSsdSize());
        Product coolerBase = productSelect.selectCheapCooler(request.getCoolingType(), request.getCoolingSize());
        Product caseBase = productSelect.selectCheapCase(request.getCaseType(), request.getCaseModel());
        Product psuBase = productSelect.selectCheapSupplyE(request.getSupplySize(), request.getSupplyType(), request.getSupplyModel());

        if (cpuBase == null || gpuBase == null || memoryBase == null || motherboardBase == null || ssdBase == null || coolerBase == null || caseBase == null || psuBase == null) {
            System.out.println("找不到相关配置");
        }

        long baseTotal = calculateTotalPrice(cpuBase, gpuBase, motherboardBase, memoryBase, ssdBase, coolerBase, caseBase, psuBase);
        if (totalBudget < baseTotal) {
            return setupBaseConfig(config, cpuBase, gpuBase, motherboardBase, memoryBase, ssdBase, coolerBase, caseBase, psuBase);
        }

        // 当前配置
        Product cpu = cpuBase, gpu = gpuBase, motherboard = motherboardBase, memory = memoryBase, ssd = ssdBase, cooling = coolerBase, psu = psuBase, caseUnit = caseBase;

        while (true) {
            // 记录当前配置的总价
            int currentTotal = (int) calculateTotalPrice(cpu, gpu, motherboard, memory, ssd, cooling, caseUnit, psu);
            int remainingBudget = totalBudget - currentTotal;

            // 判断是否满足退出条件
            if (remainingBudget <= totalBudget * 0.05) break;

            // 分配预算
            Map<String, Integer> budgetMap = budgetPortion.allocateBudget(remainingBudget);

            // 选择更高配置的组件
            Product newCpu = productSelect.selectCpu(request.getCpuType(),
                    Math.toIntExact(cpu.getPrice()),
                    (int) (budgetMap.get("cpuMin") + cpu.getPrice()),
                    (int) (budgetMap.get("cpuMax") + cpu.getPrice()));
            Product newGpu = productSelect.selectGpu(request.getGpuType(), request.getGpuModel(), request.getGpuSize(),
                    Math.toIntExact(gpu.getPrice()),
                    (int) (budgetMap.get("gpuMin") + gpu.getPrice()),
                    (int) (budgetMap.get("gpuMax") + gpu.getPrice()));
            Product newMotherboard = productSelect.selectMotherboard(request.getMotherboardType(), request.getMotherboardVersion(), request.getMotherboardModel(),
                    Math.toIntExact(motherboard.getPrice()),
                    (int) (budgetMap.get("motherboardMin") + motherboard.getPrice()),
                    (int) (budgetMap.get("motherboardMax") + motherboard.getPrice()));
            Product newMemory = productSelect.selectMemory(request.getMemoryType(), request.getMemoryModel(), request.getMemorySize(),
                    Math.toIntExact(memory.getPrice()),
                    (int) (budgetMap.get("memoryMin") + memory.getPrice()),
                    (int) (budgetMap.get("memoryMax") + memory.getPrice()));
            Product newSsd = productSelect.selectSsd(request.getSsdType(), request.getSsdSize(),
                    Math.toIntExact(ssd.getPrice()),
                    (int) (budgetMap.get("ssdMin") + ssd.getPrice()),
                    (int) (budgetMap.get("ssdMax") + ssd.getPrice()));
            Product newCooling = productSelect.selectCooler(request.getCoolingType(), request.getCoolingSize(),
                    Math.toIntExact(cooling.getPrice()),
                    (int) (budgetMap.get("coolingMin") + cooling.getPrice()),
                    (int) (budgetMap.get("coolingMax") + cooling.getPrice()));
            Product newPsu = productSelect.selectSupplyE(request.getSupplySize(), request.getSupplyType(), request.getSupplyModel(),
                    Math.toIntExact(psu.getPrice()),
                    (int) (budgetMap.get("powerSupplyMin") + psu.getPrice()),
                    (int) (budgetMap.get("powerSupplyMax") + psu.getPrice()));
            Product newCase = productSelect.selectCase(request.getCaseType(), request.getCaseModel(),
                    Math.toIntExact(caseUnit.getPrice()),
                    (int) (budgetMap.get("caseMin") + caseUnit.getPrice()),
                    (int) (budgetMap.get("caseMax") + caseUnit.getPrice()));

            // 检查是否有新的配置被选择
            boolean updated = false;

            if (newCpu != null && newCpu.getPrice() > cpu.getPrice()) {
                cpu = newCpu;
                updated = true;
            }
            if (newGpu != null && newGpu.getPrice() > gpu.getPrice()) {
                gpu = newGpu;
                updated = true;
            }
            if (newMotherboard != null && newMotherboard.getPrice() > motherboard.getPrice()) {
                motherboard = newMotherboard;
                updated = true;
            }
            if (newMemory != null && newMemory.getPrice() > memory.getPrice()) {
                memory = newMemory;
                updated = true;
            }
            if (newSsd != null && newSsd.getPrice() > ssd.getPrice()) {
                ssd = newSsd;
                updated = true;
            }
            if (newCooling != null && newCooling.getPrice() > cooling.getPrice()) {
                cooling = newCooling;
                updated = true;
            }
            if (newPsu != null && newPsu.getPrice() > psu.getPrice()) {
                psu = newPsu;
                updated = true;
            }
            if (newCase != null && newCase.getPrice() > caseUnit.getPrice()) {
                caseUnit = newCase;
                updated = true;
            }

            // 如果没有更新，说明找不到更高的配置，退出循环
            if (!updated) break;
        }

        // 设置最终的配件配置
        setupConfig(config, cpu, gpu, motherboard, memory, ssd, cooling, caseUnit, psu);
        return config;
    }

    private long calculateTotalPrice(Product... products) {
        return Arrays.stream(products).mapToLong(Product::getPrice).sum();
    }

    private Map<String, Object> setupBaseConfig(Map<String, Object> config, Product... products) {
        config.put("CPU", products[0]);
        config.put("GPU", products[1]);
        config.put("Motherboard", products[2]);
        config.put("Memory", products[3]);
        config.put("SSD", products[4]);
        config.put("Cooling", products[5]);
        config.put("Case", products[6]);
        config.put("PowerSupply", products[7]);
        return config;
    }

    private void setupConfig(Map<String, Object> config, Product cpu, Product gpu, Product motherboard, Product memory, Product ssd, Product cooling, Product caseUnit, Product psu) {
        config.put("CPU", cpu);
        config.put("GPU", gpu);
        config.put("Motherboard", motherboard);
        config.put("Memory", memory);
        config.put("SSD", ssd);
        config.put("Cooling", cooling);
        config.put("Case", caseUnit);
        config.put("PowerSupply", psu);
    }
}


