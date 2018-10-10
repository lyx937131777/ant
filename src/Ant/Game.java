package Ant;

public class Game
{
    //一个游戏的初始条件
    private int length;
    private int totalNumber;
    private int[] originPositon;
    private boolean[] originDirection;
    private int[] speed;
    //木杆和蚂蚁
    private Wood wood;
    private Ant[] ants;
    //该游戏的总时间
    private int totalTime;
    //模拟时用来记录当前时间

    private int currentTime;

    public Game(int length, int totalNumber, int[] originPositon, boolean[] originDirection, int[] speed)
    {
        //初始化数据
        this.length = length;
        this.totalNumber = totalNumber;
        this.originPositon = originPositon;
        this.originDirection = originDirection;
        this.speed = speed;
        //初始化
        initialization();
        //计算该游戏的总时间
        totalTime = 0;
        while(wood.getOnWoodNumber() > 0)
        {
            wood.move(1000);
            totalTime += 1000;
        }
        //重新初始化
        initialization();
    }

    public void initialization()
    {
        //初始化当前时间，根据初始条件生成蚂蚁和木杆
        currentTime = 0;
        ants = new Ant[totalNumber];
        for(int i = 0; i < totalNumber; i++)
        {
            ants[i] = new Ant(i,originPositon[i],originDirection[i],true,speed[i]);
        }
        wood = new Wood(length,totalNumber,totalNumber,ants);
    }

    public void simulation(int n, int incTime)
    {
        //模拟蚂蚁爬行，爬n次，每次爬0.2秒
        for(int i = 0; i < n; i++)
        {
            wood.move(incTime);
            currentTime += incTime;
            if(currentTime >= totalTime)
            {
                Room.setPause(true);
                break;
            }
        }
    }

    public int getTotalTime()
    {
        return totalTime;
    }

    public int getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(int currentTime)
    {
        this.currentTime = currentTime;
    }

    public Ant[] getAnts()
    {
        return ants;
    }
}
