package drwtcp.ftc.com.unit7_drawinggame;

import java.util.Random;

/**
 * Created by bob on 11/7/2015.
 */
public class Ball {
    static Random _randomNumberGenerator = new Random();
    // everything is in pixels.
    public int _radius;
    public int _x;
    public int _y;
    public String _name;
    public int _speed;      // pixels per move.
    public int _color;
    public int _width;
    public int _height;
    public int _points;
    public Ball(int width, int height, int radius, String name, int speed, int color, int points) {
        _radius = radius;
        _x = width/2;
        _y = height/2;
        _name = name;
        _speed = speed;
        _color = color;
        _width = width;
        _height = height;
        _points = points;
    }
    public void move() {
        int thisMove = _randomNumberGenerator.nextInt(_speed * 2 + 1);
        _x = _x +  thisMove - _speed;
        if (_x > _width-5) _x = _x - _speed*4;
        if (_x < 5) _x = _x + _speed*10;

        thisMove = _randomNumberGenerator.nextInt(_speed * 2 + 1);
        _y = _y +  thisMove - _speed;
        if (_y > _height-5) _y = _y - _speed*4;
        if (_y <5) _y = _y + _speed*10;
    }
}
