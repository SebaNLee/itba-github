# Laboratorio Helm

## Objetivo

El objetivo de este laboratorio es que te familiarices con Helm, el gestor de paquetes para Kubernetes. Vas a aprender a instalar Helm, crear charts personalizados y desplegar aplicaciones en un clúster de Kubernetes usando Helm.

Como extra, vamos a ver el uso de helmfile para gestionar varios charts de Helm desde un único archivo, lo que facilita los despliegues complejos y centraliza la configuración.

## Requisitos previos
- Un clúster de Kubernetes en funcionamiento, cualquiera de estos dos:
  - **AKS**, levantado con la guía `k8s-aks` (`./up.sh`). Es donde van a tener que desplegar el TP. Cuesta plata mientras está prendido: al terminar, `./down.sh`.
  - **Minikube** en tu máquina: `minikube start --cpus 4 --memory 8g`.
- Kubectl instalado y configurado para acceder al clúster de Kubernetes (`kubectl get nodes` tiene que responder).
- Helm (https://helm.sh/docs/intro/install/) instalado en tu máquina local.
- Helmfile (https://helmfile.readthedocs.io/en/latest/) (opcional, para la parte extra).

## Parte 1: Instalación de charts de la comunidad

En la página de Helm Hub (https://artifacthub.io/) podés encontrar una gran variedad de charts de Helm para distintas aplicaciones. En esta parte del laboratorio vamos a instalar un chart popular: PostgreSQL.

Primero, agregamos el repositorio oficial de Bitnami, que contiene el chart de PostgreSQL:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
```
Luego instalamos el chart de PostgreSQL en el clúster:
```bash
helm install my-postgresql bitnami/postgresql --version 18.12.15
```
Podés verificar que el chart se instaló correctamente con:
```bash
helm list
```
Y con kubectl podés ver los recursos creados:
```bash
kubectl get all
kubectl get pvc
```

El chart pidió un disco (PVC) y el clúster se lo dio solo: tanto AKS como Minikube traen una StorageClass **por defecto** (`default` en AKS, que crea un Azure Disk; `standard` en Minikube). Miralas con:
```bash
kubectl get storageclass
```

> Si alguna vez trabajás en un clúster sin StorageClass por defecto (por ejemplo uno armado a mano con kubeadm), el PVC y el pod quedan en `Pending` para siempre. En ese caso hay que instalar un provisionador de almacenamiento: este repo trae el chart `local-path-provisioner`, que crea la StorageClass `local-path` sobre el disco de los nodos:
> ```bash
> helm upgrade --install local-path-provisioner \
>   ./aks-demo-charts/local-path-provisioner/ \
>   --namespace local-path-storage --create-namespace \
>   --wait --timeout 5m
> ```
> y después instalás el chart con `--set primary.persistence.storageClass="local-path"`.

Lo malo de instalar un chart sin customizarlo es que no tenemos control fino sobre su configuración. Para eso podemos crear un archivo YAML personalizado.

Para ver qué parámetros se pueden configurar podés consultar la documentación del chart en Artifact Hub o generar el archivo de valores por defecto con:
```bash
helm show values bitnami/postgresql --version 18.12.15 > custom-values.yaml
```
Recomiendo usar la documentación en Artifact Hub: el archivo generado puede ser muy grande y la web tiene buscador por niveles que facilita encontrar parámetros.

Les dejo un ejemplo `values-postgresql.yaml` con algunas configuraciones personalizadas (contraseña, persistencia y recursos). Desinstalamos el chart y lo instalamos usando ese archivo:
```bash
helm uninstall my-postgresql
kubectl delete pvc --all -n default ## Helm no borra los PVC: si no, el nuevo release reusa el disco viejo con la contraseña vieja
helm install my-postgresql bitnami/postgresql --version 18.12.15 -f values-postgresql.yaml
```
Verificá la instalación con:
```bash
helm list
kubectl get all
kubectl get pvc   # ahora pide 4Gi, como dice el values
```
Y probá que la contraseña que pusiste en el values es la que quedó:
```bash
kubectl exec -it my-postgresql-0 -- env PGPASSWORD=passwordPropia psql -U postgres -c 'select version();'
```

La mayoría de los charts vienen bien armados y requieren pocos cambios, pero en algunos casos vas a tener que ajustar parámetros como recursos, credenciales, storageClass, tamaño de disco, etc., para que la aplicación funcione en tu entorno.

### Ojo con los charts de Bitnami (cambio de agosto 2025)

Bitnami fue durante años la fuente "por defecto" de charts, pero desde agosto de 2025 cambió su modelo y eso te va a afectar si los usás en el TP:

- En `docker.io/bitnami` quedaron **pocas imágenes y solo con el tag `latest`**. Todas las versiones con número se movieron a `docker.io/bitnamilegacy`: siguen ahí, pero **congeladas y sin parches de seguridad**.
- Los charts se siguen publicando, pero los de PostgreSQL y MongoDB ahora apuntan a `:latest`. Pinear el chart (`--version 18.12.15`) ya **no** pinea la versión de la base: si Bitnami publica un PostgreSQL nuevo, tu próximo pod lo baja.
- Algunas aplicaciones, como RabbitMQ, salieron del catálogo: su chart quedó congelado y la imagen que trae por defecto **ya no existe** (el pod queda en `ErrImagePull`). En la Parte 2 lo resolvemos apuntando a `bitnamilegacy` (mirá `aks-demo-charts/values-rabbitmq.yaml`).

Antes de instalar un chart, fijate qué imagen va a bajar:
```bash
helm show values bitnami/postgresql --version 18.12.15 | grep -A4 '^image:'
```

## Parte 2: Crear un chart personalizado

En esta parte vamos a crear nuestros propios charts de Helm para desplegar una aplicación. Usaremos como ejemplo los manifests que provee Azure para AKS-Demo y los replicaremos en charts.

La estructura básica de un chart de Helm es:
```
mychart/
├─ Chart.yaml
├─ values.yaml
├─ templates/
│  ├─ deployment.yaml
│  ├─ service.yaml
│  └─ ingress.yaml
```

Esta estructura puede variar según la complejidad del chart, pero estos son los archivos principales.

Para crear un chart llamado `myapp`:
```bash
helm create myapp
```
Eso genera la estructura básica en la carpeta `myapp`.

En la carpeta `templates` vas a ver varios archivos de ejemplo; si no necesitás cambios muy específicos podés ignorarlos y configurar la aplicación desde el `values.yaml` que Helm genera.

Muchos recursos como HPA, Ingress o ServiceAccount sólo se crean si los habilitás en `values.yaml`. Si necesitás un tipo de recurso distinto al Deployment (por ejemplo DaemonSet o StatefulSet), tenés que crear la plantilla correspondiente y eliminar `deployment.yaml`.

No les voy a pedir que creen los charts para reproducir `yamls-aks-store`: ya los incluí en la carpeta `aks-demo-charts`. Sí les dejo los puntos más importantes a tener en cuenta:

1. Chart.yaml: contiene la metadata del chart (nombre, versión, descripción). Actualizalo con la info de tu aplicación. Ojo que hay dos versiones distintas:
   - `version` es la versión **del chart** (cambia cuando cambiás templates o values).
   - `appVersion` es la versión **de la aplicación**. En nuestros charts es `2.2.0`, y como en `values.yaml` dejamos `image.tag: ""`, el deployment usa `{{ .Values.image.tag | default .Chart.AppVersion }}`: la imagen queda pineada a `2.2.0` y no a `latest`, que cambia sin avisar.
2. Environment variables: por defecto no vienen configuradas en `deployment.yaml`. Normalmente se definen en `values.yaml` y se referencian en la plantilla del deployment. Por ejemplo, en `values.yaml`:
   ```yaml
    env:
      - name: MY_SPECIAL_VAR
        value: somevalue
   ```
   Y en `deployment.yaml` podés incluir:
   ```yaml
      containers:
        - name: {{ .Chart.Name }}
        {{- if .Values.env }}
        env:
          {{- toYaml .Values.env | nindent 12 }}
        {{- end }}
   ```
3. StorageClass: si la aplicación necesita persistencia, el tamaño y la StorageClass se configuran en `values.yaml`. Si no ponés `storageClass`, se usa la del clúster por defecto, que es lo que hacemos acá: el mismo values sirve para AKS y para Minikube. Ejemplo en `values.yaml`:
   ```yaml
   persistence:
     # storageClass: "local-path"   # sólo si tu clúster no tiene una por defecto
     size: 8Gi
   ```
4. Servicios: configurá puertos y tipo de servicio (ClusterIP, NodePort, LoadBalancer) en `values.yaml`. Si necesitás NodePort, agregá la configuración en `service.yaml`. Por ejemplo:
    ```yaml
      ports:
        - port: {{ .Values.service.port }}
          targetPort: http
          protocol: TCP
          name: http
          {{- if eq .Values.service.type "NodePort" }}
          nodePort: {{ .Values.service.nodePort }}
          {{- end }}
    ```
   En AKS los nodos no tienen IP pública, así que un NodePort no se puede alcanzar desde afuera. Por eso `store-front` y `store-admin` son de tipo **LoadBalancer**: AKS les asigna una IP pública (como en la guía de AKS) y en Minikube los abrís con `minikube service`.
5. Recursos: definí recursos (CPU y memoria) en `values.yaml` para asegurar que la aplicación tenga lo necesario. Ejemplo:
   ```yaml
   resources:
     limits:
       cpu: 500m
       memory: 512Mi
     requests:
       cpu: 250m
       memory: 256Mi
   ```

Ahora que los charts están listos, podés desplegarlos en el clúster. Si los instalás uno por uno, el orden puede importar; para facilitarlo incluí un script:
```bash
cd aks-demo-charts
./deploy-charts.sh
```

Verificá la instalación con:
```bash
helm list -n aks-demo
kubectl get all -n aks-demo
```
Deberías ver todos los pods en Running. Para entrar a la tienda:

- **AKS**: esperá a que los servicios tengan `EXTERNAL-IP` (tarda uno o dos minutos) y entrá a `http://<EXTERNAL-IP>:8080` (tienda) y `http://<EXTERNAL-IP>:8081` (admin):
  ```bash
  kubectl get svc -n aks-demo store-front store-admin -w
  ```
- **Minikube**: estos comandos te imprimen la URL de cada uno:
  ```bash
  minikube service -n aks-demo store-front --url
  minikube service -n aks-demo store-admin --url
  ```

`virtual-customer` y `virtual-worker` generan y despachan pedidos solos: a los pocos minutos los vas a ver aparecer en el admin. También podés seguirlos con `kubectl logs -n aks-demo deploy/virtual-customer -f`.


## Parte 3 (Extra): Gestionar múltiples charts con Helmfile

En esta parte extra veremos cómo usar Helmfile para gestionar varios charts desde un solo archivo, lo que centraliza la configuración y simplifica los despliegues.

Levantar varios charts uno por uno es tedioso; con Helmfile podés declarar todos los charts que querés desplegar, el orden, los valores, etc., en `helmfile.yaml` y aplicarlos con un solo comando.

Si venís de la Parte 2, primero borrá lo que instalaste con el script, así Helmfile arranca de cero:
```bash
cd aks-demo-charts
for r in $(helm list -n aks-demo -q); do helm uninstall $r -n aks-demo; done
kubectl delete pvc --all -n aks-demo
```

Ejemplo de uso: desde la carpeta `aks-demo-charts` inicializá helmfile:
```bash
helmfile init
# Instala los plugins de helm que usa helmfile. El único imprescindible es "diff"; al resto podés decirle que no.
```
Si te dice que tu versión de Helm es vieja y te ofrece instalar una nueva, mejor actualizala vos siguiendo https://helm.sh/docs/intro/install/ y volvé a correr `helmfile init`.

Después desplegás todo con:
```bash
helmfile apply
```
Helmfile va a leer `helmfile.yaml` del directorio actual y desplegar los charts definidos. Fijate que usa los mismos `values-mongodb.yaml` y `values-rabbitmq.yaml` que el script, y que fija la versión de cada chart con `version:`. Verificá con:
```bash
helm list -n aks-demo
kubectl get all -n aks-demo
helmfile list #Para ver los que tiene helmfile
```

Para destruir todo:
```bash
helmfile destroy
kubectl delete pvc --all -n aks-demo   # los discos no se borran solos
```

En `helmfile.yaml` podés usar la directiva `needs` para declarar dependencias entre charts (por ejemplo, que el backend espere a que la base de datos esté lista). Helmfile respeta ese orden.
```yaml
releases:
  - name: my-release
    chart: ./my-chart
    needs: 
      - postgres
```
