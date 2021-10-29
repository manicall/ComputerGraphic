package com.example.lab2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Render {
    Canvas canvas;

    Render(Canvas canvas) {
        this.canvas = canvas;
    }

    // y = ax^2, a > 0 (1)
    void firstCurve(float a) {
        int x, y;
        float delta;
        x=0; y=0; delta=0;		//начальная точка (x0,y0))
        while (2*a*x<1)			//пока вектор касательной принадлежит 1-й
        {				//октанте
            canvas.drawPoint(x, -y, new Paint()); 	//ставим точку с координатами (x,y)
            if (delta<0)
                delta+=a*(2*x+1); //положительное приращение
            else
            {			//отрицательное приращение
                delta+=a*(2*x+1)-1;
                y++;
            }
            x++;
        }
        while (y<canvas.getHeight()/2)			//пока вектор касательной принадлежит
        {				//четвертой октанте
            canvas.drawPoint(x, -y, new Paint()); 		//ставим точку с координатами (x,y)
            if (delta>=0)
                delta += -1;    //отрицательное приращение
            else
            {			//положительное приращение
                delta += a*(2*x+1)-1;
                x++;
            }
            y++;
        }

    }

    // ay^3 - x = 0, a > 0 (1)
    void secondCurve(float a) {
        int x, y;
        float delta;
        x=0; y=0; delta=0;		//начальная точка (x0,y0))
        while (3*a*pow(y,2) < 1)			//пока вектор касательной принадлежит 1-й
        {				//октанте
            canvas.drawPoint(x, -y, new Paint()); 	//ставим точку с координатами (x,y)
            if (delta<0)
                delta+= 3*a*pow(y,2) + 3*a*y + a; //положительное приращение
            else
            {			//отрицательное приращение
                delta+= 3*a*pow(y,2) + 3*a*y + a - 1;
                x++;
            }
            y++;
        }
        while (x < canvas.getWidth() / 2)			//пока вектор касательной принадлежит
        {				//четвертой октанте
            canvas.drawPoint(x, -y, new Paint()); 		//ставим точку с координатами (x,y)
            if (delta>=0)
                delta += -1;    //отрицательное приращение
            else
            {			//положительное приращение
                delta += 3*a*pow(y,2) + 3*a*y + a - 1;
                y++;
            }
            x++;
        }

    }
    // x^2 / a^2 - y^2 / b^2 = 1 (4)
    void thirdCurve(float a, float b){
        float x, y;
        float delta;
        x=a; y=0; delta=0;		//начальная точка (x0,y0))
        int j = 0;
        while (pow(b,2) * x < pow(a,2) * y)
        {
            canvas.drawPoint(x, -y, new Paint()); 	//ставим точку с координатами (x,y)
            if (delta<0)
                delta+= pow(b,2)*(2*x + 1) + pow(a,2) * (2*y-1); //положительное приращение
            else
            {			//отрицательное приращение
                delta+= pow(a,2) * (2*y-1);
                x++;
            }
            y--;


            if(++j > 1000) break;
        }
        /*while (x < canvas.getWidth() / 2)			//пока вектор касательной принадлежит
        {				//четвертой октанте
            canvas.drawPoint(x, -y, new Paint()); 		//ставим точку с координатами (x,y)
            if (delta>=0)
                delta += pow(b,2) * (2*y-1);    //отрицательное приращение
            else
            {			//положительное приращение
                delta += pow(b,2)*(2*x + 1) + pow(a,2) * (2*y-1);
                y--;
            }
            x++;
        }*/


    }

    void example(int a, int b){
        int a2 = a * a, b2 = b * b, x = a, y = 0, delta = 0;    // начальная точка (x0,y0))

        //b2x2+a2y2-a2b2=0

        while (b2 * x > a2 * y)    // пока вектор касательной принадлежит
        {                // третьей октанте
            canvas.drawPoint(x, -y, new Paint());        // ставим точку (x,y)
            if (delta < 0)
                delta += a2 * (y * 2 + 1); // положительное приращение
            else {    // отрицательное приращение
                delta += a2 * (y * 2 + 1) + b2 * (-x * 2 + 1);
                x--;    // x уменьшается на 1
            }
            y++;        // y увеличивается на 1
        }

        while (x >= 0)  // пока вектор касательной принадлежит
        {                // четвертрй октанте
            canvas.drawPoint(x, -y, new Paint());    // ставим точку (x,y)
            if (delta >= 0)
                delta += b2 * (-x * 2 + 1); // отрицательное приращение
            else {            // положительное приращение
                delta += a2 * (y * 2 + 1) + b2 * (-x * 2 + 1);
                y++;
            }            // y увеличивается на 1
            x--;            // x уменьшается на 1
        }
    }

    double pow(double a, double b){
       return Math.pow(a, b);
    }

}
