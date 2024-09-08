package org.orca.sample;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

public class NSGAIIExample {

    // Định nghĩa bài toán tối ưu với 2 hàm mục tiêu
    public static class MyProblem extends AbstractProblem {

        public MyProblem() {
            super(1, 2); // 1 biến đầu vào, 2 hàm mục tiêu
        }

        @Override
        public void evaluate(org.moeaframework.core.Solution solution) {
            double x = ((RealVariable) solution.getVariable(0)).getValue();

            // Hàm mục tiêu f1 = x^2
            solution.setObjective(0, x * x);

            // Hàm mục tiêu f2 = (x - 2)^2
            solution.setObjective(1, (x - 2) * (x - 2));
        }

        @Override
        public org.moeaframework.core.Solution newSolution() {
            org.moeaframework.core.Solution solution = new org.moeaframework.core.Solution(1, 2);
            solution.setVariable(0, new RealVariable(-5.0, 5.0)); // Giới hạn biến từ -5 đến 5
            return solution;
        }
    }

    public static void main(String[] args) {
        // Sử dụng NSGA-II với bài toán tối ưu MyProblem
        NondominatedPopulation result = new Executor()
                .withProblemClass(MyProblem.class)
                .withAlgorithm("NSGAII")
                .withMaxEvaluations(10000)
                .distributeOnAllCores()
                .run();

        // In ra kết quả (các nghiệm không trội)
        System.out.println("Non-dominated solutions:");
        for (org.moeaframework.core.Solution solution : result) {
            System.out.println(solution.getObjective(0) + " " + solution.getObjective(1));
        }
    }
}

