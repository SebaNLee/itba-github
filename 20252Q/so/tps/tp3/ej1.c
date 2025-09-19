



typedef int sem_t;
sem_t s;


// básicamente los que están en el sistema son llamados por los wrappers de acá
// los implemento directo, sacándole la capa, como si fuesen de sistema

void my_init(int * s, int value)
{
    *s = value;
}

void my_down(int * s)
{
    while(*s <= 0)
    {
        continue; // espera activa
    }
    
    *s = (*s)--;
}

void my_up(int * s)
{
    *s = (*s)++;
}