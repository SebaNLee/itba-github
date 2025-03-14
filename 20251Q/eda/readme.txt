al principio iba todo ok en la nb, pero tuve problemas al intentar correr proyectos de maven y normales en la pc

instalar openjdk-17 (que es lo que pedía Maven) solucionó los problemas con Maven

lo que hice para arreglar el normal fue agregar .vscode/settings.json en root que define dónde están los source path,
para luego manejarme con packages