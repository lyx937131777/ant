package Ant;

import static org.junit.Assert.*;

public class RoomTest
{
    //保留了一些需要的变量，去掉了跟界面有关的变量
    private static int currentSituation;//当前情况，0-31
    private static int totalSituation;//总情况数
    private Game game = null;//游戏类
    //四个初始条件
    private int length;
    private int[] originPosition;
    private boolean[] originDirection;
    private static final int antNumber = 5;
    private static final int[] speed = {5, 5, 5, 5, 5, 5};
    //最大最小值
    private int minTime;
    private int maxTime;

    @org.junit.Before
    public void setUp() throws Exception
    {
        //初始化总情况数，当前情况设为0，初始化最大最小值和蚂蚁的初始条件，生成第一种游戏
        currentSituation = 0;
        totalSituation = 1;
        for(int i = 0; i < antNumber; i++)
        {
            totalSituation *= 2;
        }
        minTime = 9999;
        maxTime = 0;
        length = 300;
        int a[] = {30, 80, 110, 160, 250};
        boolean b[] = {false, false, false, false, false};
        originPosition = a;
        originDirection = b;
        game = new Game(length, antNumber, originPosition, originDirection, speed);
    }

    @org.junit.After
    public void tearDown() throws Exception
    {
    }

    @org.junit.Test
    public void check_min_and_max()
    {
        //执行所有种蚂蚁爬杆情况，并给出最小和最大值
        //该测试用于测试最小最大值计算是否正确
        while (currentSituation < totalSituation-1)
        {
            int time = playGame()/1000;
            currentSituation++;
            //System.out.println(currentSituation + " : " + time);
            if (time > maxTime)
            {
                maxTime = time;
            }
            if (time < minTime)
            {
                minTime = time;
            }
            playGame();
        }
        assertEquals(28,minTime);
        assertEquals(54,maxTime);
        //两个都PASS，说明结果正确
    }

    //要用到的方法
    public int playGame()
    {
        //根据currentSituation的值确定并生成游戏，同时返回该种情况下蚂蚁爬行的总时间，单位毫秒
        originDirection = new boolean[antNumber];
        for(int j = 0; j < antNumber; j++)
        {
            originDirection[antNumber-1-j] = (currentSituation & (0x01 << j)) != 0;
        }
        game = new Game(length,antNumber,originPosition,originDirection,speed);
        return game.getTotalTime();
    }


}