package test;

import SGP.CA.Domain.*;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date dateBluePrint = new Date(2021, 10, 28);
        BluePrint testBluePrint = new BluePrint("Titulo de prueba", dateBluePrint, "No", "Completado", "Co-director", 20, "Presencial", "Estudiante1", "Descripcion");
        System.out.println(testBluePrint);

        Date startDateWorkPlan = new Date(2021, 11, 17);
        Date endDateWorkPlan = new Date(2021, 9, 19);
        WorkPlan workPlanTest = new WorkPlan("01234", startDateWorkPlan, endDateWorkPlan, "Objetivo");
        System.out.println(workPlanTest);

        Date endDateInvestigation = new Date(2021, 8, 17);
        Date starDateInvestigation = new Date(2021, 7, 1);
        InvestigationProject investigationProjectTest = new InvestigationProject("Titulo del proyecto", endDateInvestigation, starDateInvestigation, "Si", "Participantes");
        System.out.println(investigationProjectTest);

        Objective objectiveTest = new Objective("Titulo de objetivo", "Descripcion de objetivo", "Estrategia1");
        System.out.println(objectiveTest);

        Strategy strategyTest = new Strategy(1, "Estrategia1", "Meta 1", "Accion 1", "Resultado 1");
        System.out.println(strategyTest);
    }
}
