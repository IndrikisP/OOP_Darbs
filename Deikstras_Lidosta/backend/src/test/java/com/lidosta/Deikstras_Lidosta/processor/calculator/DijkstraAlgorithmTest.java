package com.lidosta.Deikstras_Lidosta.processor.calculator;

import com.lidosta.Deikstras_Lidosta.processor.calculator.response.FlightsInternalInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DijkstraAlgorithmTest {
    private List<Integer> distances = new ArrayList<>();
    private List<Integer> testDistances = new ArrayList<>();

    private int counter = 0;
    private int testCounter = 0;


    @Before
    public void setUp() {
        distances = new ArrayList<>();
        testDistances = new ArrayList<>();
        counter = 0;
        testCounter = 0;
    }

    /**
     * Checks if all paths algorithm on 3x3 input
     */
    @Test
    public void scenario1() {
        int size = 3;
        distances.add(0);
        distances.add(1);
        distances.add(5);
        distances.add(1);
        distances.add(0);
        distances.add(2);
        distances.add(5);
        distances.add(2);
        distances.add(0);

        testDistances.add(0);
        testDistances.add(1);
        testDistances.add(3);
        testDistances.add(1);
        testDistances.add(0);
        testDistances.add(2);
        testDistances.add(3);
        testDistances.add(2);
        testDistances.add(0);
        List<FlightsInternalInfo>[][] tmpActual = new List[size][size];
        List<FlightsInternalInfo>[][] expected = new List[size][size];

        beforeAction(tmpActual, expected);
        List<FlightsInternalInfo>[][] actual = executeScenario(tmpActual);
        afterAction(expected, actual);

    }
    /**
     * Checks if all paths algorithm on 5x5 input
     */
    @Test
    public void scenario2() {
        int size = 5;
        distances.add(0);
        distances.add(10);
        distances.add(5);
        distances.add(7);
        distances.add(1);
        distances.add(10);
        distances.add(0);
        distances.add(2);
        distances.add(3);
        distances.add(8);

        distances.add(5);
        distances.add(2);
        distances.add(0);
        distances.add(2);
        distances.add(20);
        distances.add(7);
        distances.add(3);
        distances.add(2);
        distances.add(0);
        distances.add(4);

        distances.add(1);
        distances.add(8);
        distances.add(20);
        distances.add(4);
        distances.add(0);

        testDistances.add(0);
        testDistances.add(7);
        testDistances.add(5);
        testDistances.add(5);
        testDistances.add(1);
        testDistances.add(7);
        testDistances.add(0);
        testDistances.add(2);
        testDistances.add(3);
        testDistances.add(7);
        testDistances.add(5);
        testDistances.add(2);
        testDistances.add(0);
        testDistances.add(2);
        testDistances.add(6);
        testDistances.add(5);
        testDistances.add(3);
        testDistances.add(2);
        testDistances.add(0);
        testDistances.add(4);
        testDistances.add(1);
        testDistances.add(7);
        testDistances.add(6);
        testDistances.add(4);
        testDistances.add(0);

        List<FlightsInternalInfo>[][] tmpActual = new List[size][size];
        List<FlightsInternalInfo>[][] expected = new List[size][size];

        beforeAction(tmpActual, expected);
        List<FlightsInternalInfo>[][] actual = executeScenario(tmpActual);
        afterAction(expected, actual);

    }

    public List<FlightsInternalInfo>[][] executeScenario(List<FlightsInternalInfo>[][] pDInfoPrice) {
        List<FlightsInternalInfo>[][] pDInfoPricen = pDInfoPrice;
        AllPathsAlgorithm allPathsAlgorithm = new AllPathsAlgorithm();
        allPathsAlgorithm.getAllFlights(pDInfoPricen);
        return pDInfoPricen;
    }

    void beforeAction(List<FlightsInternalInfo>[][] pDInfoPrice, List<FlightsInternalInfo>[][] testPDInfoPrice) {
        for (int i = 0; i < pDInfoPrice.length; i++) {
            for (int j = 0; j < pDInfoPrice.length; j++) {
                List<List<FlightsInternalInfo>> tmp = createSamplePriceDistanceInfoList();
                pDInfoPrice[i][j] = tmp.get(0);
                testPDInfoPrice[i][j] = tmp.get(1);
            }
        }
    }

    void afterAction(List<FlightsInternalInfo>[][] expected, List<FlightsInternalInfo>[][] actual) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected.length; j++) {
                //Assert.assertEquals(expected[i][j].get(0).getDistance(), actual[i][j].get(0).getDistance());
                System.out.println("pDInfonew[" + i + "][" + j + "]: " + actual[i][j]);
            }
        }
    }

    private List<List<FlightsInternalInfo>> createSamplePriceDistanceInfoList() {
        List<FlightsInternalInfo> actual = new ArrayList<>();
        List<FlightsInternalInfo> expected = new ArrayList<>();

        FlightsInternalInfo info1 = new FlightsInternalInfo(UUID.randomUUID(), 100.0f, distances.get(counter++));
        FlightsInternalInfo info2 = new FlightsInternalInfo(UUID.randomUUID(), 100.0f, testDistances.get(testCounter++));

        actual.add(info1);
        expected.add(info2);
        return Arrays.asList(actual, expected);
    }

    @Getter
    @AllArgsConstructor
    private static class InputParameters {
        private final List<FlightsInternalInfo>[][] pDInfoPrice = new List[3][3];
    }
}