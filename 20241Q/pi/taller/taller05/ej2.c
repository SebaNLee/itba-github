// hago directamente la función para el código de testeo

#define R 0.2989
#define G 0.5870
#define B 0.1140
 
#define N 3
#define M 5
#define RGB_M (3 * M)

// copiado del prototipo
void rgbToGray(const unsigned char (rgb[])[RGB_M], unsigned char grayscale[][M])
{
    for(int i = 0; i < N; i++)
    {
        for(int j = 0, k = 0; j < RGB_M; j += 3, k++)
        {
            grayscale[i][k] = rgb[i][j] * R + rgb[i][j + 1] * G + rgb[i][j + 2] * B;
        }
    }
}


// otra posibilidad es no usar el índice auxiliar e utilizar directametne el índice j para acceder a rgb[][]
// sería con j*3, (j*3)+1, (j*3)+2

// gcc ej2.c ej2_test.c -o ej2 -Wall -pedantic -std=c99