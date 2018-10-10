package Ant;

public class Ant
{
    private int number;//蚂蚁编号
    private int position;//蚂蚁位置
    private boolean direction;//0表示左，1表示右
    private boolean onWood;//是否在木杆上
    private int speed;//速度

    public Ant(int number, int position, boolean direction, boolean onWood, int speed)
    {
        //初始化
        this.number = number;
        this.position = position;
        this.direction = direction;
        this.onWood = onWood;
        this.speed = speed;
    }

    public void move(int time)
    {
        //如果蚂蚁在杆上，则让蚂蚁朝自己的方向爬行0.2秒
        if(onWood)
        {
            if(direction)
            {
                position += speed*time/1000;
            }else
            {
                position -= speed*time/1000;
            }
        }
    }

    public void turnHead()
    {
        //令蚂蚁调头
        direction = !direction;
    }
    public void dropWood()
    {
        //令蚂蚁掉落杆子
        onWood = false;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public boolean isDirection()
    {
        return direction;
    }

    public void setDirection(boolean direction)
    {
        this.direction = direction;
    }

    public boolean isOnWood()
    {
        return onWood;
    }

    public void setOnWood(boolean onWood)
    {
        this.onWood = onWood;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
}
