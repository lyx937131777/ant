package Ant;

public class Wood
{
    //初始条件
    private int length;
    private int totalNumber;
    //当前蚂蚁数
    private int onWoodNumber;
    //蚂蚁实例
    private Ant[] ants;

    public Wood(int length, int totalNumber, int onWoodNumber, Ant[] ants)
    {
        //初始化数据
        this.length = length;
        this.totalNumber = totalNumber;
        this.onWoodNumber = onWoodNumber;
        this.ants = ants;
    }

    public void move(int time)
    {
        //模拟爬行
        //先判断是否碰撞，若碰撞则令两只蚂蚁调头
        for (int i = 0; i < totalNumber - 1; i++)
        {
            if (ants[i].isOnWood() && ants[i + 1].isOnWood() && ants[i].getPosition() == ants[i + 1].getPosition() && ants[i].isDirection() && !ants[i + 1].isDirection())
            {
                ants[i].turnHead();
                ants[i + 1].turnHead();
            }
        }
        //令妹纸蚂蚁都爬行0.2秒
        for (int i = 0; i < totalNumber; i++)
        {
            ants[i].move(time);
        }
        //判断蚂蚁是否掉落，掉落后令当前蚂蚁数减1
        for (int i = 0; i < totalNumber; i++)
        {
            if (ants[i].isOnWood() && ants[i].getPosition() == 0 && !ants[i].isDirection())
            {
                ants[i].dropWood();
                onWoodNumber--;
            }
            if (ants[i].isOnWood() && ants[i].getPosition() == length && ants[i].isDirection())
            {
                ants[i].dropWood();
                onWoodNumber--;
            }
        }
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getTotalNumber()
    {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber)
    {
        this.totalNumber = totalNumber;
    }

    public int getOnWoodNumber()
    {
        return onWoodNumber;
    }

    public void setOnWoodNumber(int onWoodNumber)
    {
        this.onWoodNumber = onWoodNumber;
    }
}
