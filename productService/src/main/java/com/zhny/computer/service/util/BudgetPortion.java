package com.zhny.computer.service.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BudgetPortion {

    public Map<String, Integer> allocateBudget(Integer totalBudget) {
        // 定义内存、电源、SSD、散热器和机箱的固定预算比例
        double memoryPercentage = 0.10;    // 内存固定10%
        double powerSupplyPercentage = 0.05; // 电源固定5%
        double ssdPercentage = 0.10;       // SSD 固定10%
        double coolingPercentage = 0.05;   // 散热器固定5%
        double casePercentage = 0.05;      // 机箱固定5%

        // 存储所有配件的预算
        Map<String, Integer> budgetMap = new HashMap<>();

        // 调用回溯算法分配预算
        boolean success = allocateWithBacktracking(totalBudget, budgetMap, memoryPercentage,
                powerSupplyPercentage, ssdPercentage, coolingPercentage, casePercentage);

        if (!success) {
            System.out.println("未能找到满足所有配件需求的预算分配。");
        }

        return budgetMap;
    }

    // 动态调整 CPU 的预算比例
    private double calculateCpuPercentage(int totalBudget) {
        if (totalBudget > 10000) {
            return 0.30;  // 高预算时 CPU 占比提升到30%
        } else if (totalBudget > 6000) {
            return 0.25;  // 中等预算时 CPU 占比25%
        }
        return 0.20;  // 默认20%
    }

    // 动态调整 GPU 的预算比例
    private double calculateGpuPercentage(int totalBudget) {
        if (totalBudget > 20000) {
            return 0.40;  // 高预算时 GPU 占比40%
        } else if (totalBudget > 10000) {
            return 0.35;  // 中等预算时 GPU 占比35%
        }
        return 0.30;  // 默认30%
    }

    // 动态调整主板的预算比例
    private double calculateMotherboardPercentage(int totalBudget) {
        if (totalBudget > 20000) {
            return 0.20;  // 高预算时主板占比20%
        } else if (totalBudget > 10000) {
            return 0.18;  // 中等预算时主板占比18%
        }
        return 0.15;  // 默认15%
    }

    private boolean allocateWithBacktracking(int totalBudget, Map<String, Integer> budgetMap,
                                             double memoryPercentage, double powerSupplyPercentage,
                                             double ssdPercentage, double coolingPercentage,
                                             double casePercentage) {
        int remainingBudget = totalBudget;

        // 动态计算 CPU、GPU 和主板的预算比例
        double cpuPercentage = calculateCpuPercentage(totalBudget);
        double gpuPercentage = calculateGpuPercentage(totalBudget);
        double motherboardPercentage = calculateMotherboardPercentage(totalBudget);

        // 选择 CPU
        budgetMap.put("cpuMin", (int) (remainingBudget * cpuPercentage * 0.7));
        budgetMap.put("cpuMax", Math.min((int) (remainingBudget * cpuPercentage * 1.3), 5000));  // 加入上限控制
        remainingBudget -= budgetMap.get("cpuMax");

        // 选择 GPU
        budgetMap.put("gpuMin", (int) (remainingBudget * gpuPercentage * 0.7));
        budgetMap.put("gpuMax", Math.min((int) (remainingBudget * gpuPercentage * 1.3), 40000));  // 加入上限控制
        remainingBudget -= budgetMap.get("gpuMax");

        // 选择主板
        budgetMap.put("motherboardMin", (int) (remainingBudget * motherboardPercentage * 0.7));
        budgetMap.put("motherboardMax", Math.min((int) (remainingBudget * motherboardPercentage * 1.3), 5000));
        remainingBudget -= budgetMap.get("motherboardMax");

        if (remainingBudget < 0) {
            return backtrackAndAdjust(totalBudget, budgetMap, cpuPercentage, gpuPercentage,
                    motherboardPercentage, memoryPercentage, powerSupplyPercentage, ssdPercentage,
                    coolingPercentage, casePercentage);
        }

        // 选择内存
        budgetMap.put("memoryMin", (int) (remainingBudget * memoryPercentage * 0.7));
        budgetMap.put("memoryMax", Math.min((int) (remainingBudget * memoryPercentage * 1.3), 4000));
        remainingBudget -= budgetMap.get("memoryMax");

        if (remainingBudget < 0) {
            return backtrackAndAdjust(totalBudget, budgetMap, cpuPercentage, gpuPercentage,
                    motherboardPercentage, memoryPercentage, powerSupplyPercentage, ssdPercentage,
                    coolingPercentage, casePercentage);
        }

        // 选择电源
        budgetMap.put("powerSupplyMin", (int) (remainingBudget * powerSupplyPercentage * 0.7));
        budgetMap.put("powerSupplyMax", Math.min((int) (remainingBudget * powerSupplyPercentage * 1.3), 4000));
        remainingBudget -= budgetMap.get("powerSupplyMax");

        if (remainingBudget < 0) {
            return backtrackAndAdjust(totalBudget, budgetMap, cpuPercentage, gpuPercentage,
                    motherboardPercentage, memoryPercentage, powerSupplyPercentage, ssdPercentage,
                    coolingPercentage, casePercentage);
        }

        // 选择 SSD
        budgetMap.put("ssdMin", (int) (remainingBudget * ssdPercentage * 0.7));
        budgetMap.put("ssdMax", Math.min((int) (remainingBudget * ssdPercentage * 1.3), 4000));
        remainingBudget -= budgetMap.get("ssdMax");

        if (remainingBudget < 0) {
            return backtrackAndAdjust(totalBudget, budgetMap, cpuPercentage, gpuPercentage,
                    motherboardPercentage, memoryPercentage, powerSupplyPercentage, ssdPercentage,
                    coolingPercentage, casePercentage);
        }

        // 选择散热器
        budgetMap.put("coolingMin", (int) (remainingBudget * coolingPercentage * 0.7));
        budgetMap.put("coolingMax", Math.min((int) (remainingBudget * coolingPercentage * 1.3), 4000));
        remainingBudget -= budgetMap.get("coolingMax");

        if (remainingBudget < 0) {
            return backtrackAndAdjust(totalBudget, budgetMap, cpuPercentage, gpuPercentage,
                    motherboardPercentage, memoryPercentage, powerSupplyPercentage, ssdPercentage,
                    coolingPercentage, casePercentage);
        }

        // 选择机箱
        budgetMap.put("caseMin", (int) (remainingBudget * casePercentage * 0.7));
        budgetMap.put("caseMax", Math.min((int) (remainingBudget * casePercentage * 1.3), 4500));

        return remainingBudget >= 0;
    }

    // 回溯并调整预算比例
    private boolean backtrackAndAdjust(int totalBudget, Map<String, Integer> budgetMap,
                                       double cpuPercentage, double gpuPercentage,
                                       double motherboardPercentage, double memoryPercentage,
                                       double powerSupplyPercentage, double ssdPercentage,
                                       double coolingPercentage, double casePercentage) {
        // 逐步降低每个配件的预算比例以释放更多预算
        if (cpuPercentage > 0.10) {
            cpuPercentage -= 0.05;
        } else if (gpuPercentage > 0.15) {
            gpuPercentage -= 0.05;
        } else if (motherboardPercentage > 0.10) {
            motherboardPercentage -= 0.05;
        } else if (memoryPercentage > 0.05) {
            memoryPercentage -= 0.03;
        } else if (powerSupplyPercentage > 0.01) {
            powerSupplyPercentage -= 0.01;
        } else if (ssdPercentage > 0.01) {
            ssdPercentage -= 0.01;
        } else if (coolingPercentage > 0.01) {
            coolingPercentage -= 0.01;
        } else if (casePercentage > 0.01) {
            casePercentage -= 0.01;
        }

        // 重新分配预算
        return allocateWithBacktracking(totalBudget, budgetMap, memoryPercentage,
                powerSupplyPercentage, ssdPercentage, coolingPercentage, casePercentage);
    }
}
