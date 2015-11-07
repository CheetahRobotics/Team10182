package drwtcp.ftc.com.unit7_drawinggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BobsView extends ImageView implements Runnable {
    private List<Ball> _balls = new ArrayList<Ball>();
    private boolean _firstTime = true;
    public BobsView(Context context) {
        super(context);
    }
    public BobsView(Context context, AttributeSet aSet) {
        super(context, aSet);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (_firstTime) {
            _balls.add(new Ball(getWidth(), getHeight(), 40, "Quaffle", 4, Color.RED, 1));
            _balls.add(new Ball(getWidth(), getHeight(), 40, "Quaffle", 4, Color.RED, 1));
            _balls.add(new Ball(getWidth(), getHeight(), 10, "Golden Snitch", 20, Color.YELLOW, 5));
            _firstTime = false;
        }
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);

        for (Ball ball : _balls) {
            ball.move();
            paint.setColor(ball._color);
            canvas.drawCircle(ball._x, ball._y, ball._radius, paint);
        }

        // Invalidate view at about 60fps
        postDelayed(this, 16);
    }
    public int onUserTouch(int x, int y) {
        Log.i("MainActivity", "Touch coordinates: " + String.valueOf(x) + "x" + String.valueOf(y));
        return 0;
    }
    public void run() {
        // Request a redraw of this view
        // onDraw(Canvas) will be called
        invalidate();
    }}
