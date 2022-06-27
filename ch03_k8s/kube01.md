# 쿠버네티스
도커 오케스트레이션 중 가장 많이 쓰이는 제품이다.

* 쿠버네티스는 모놀리식(monolithic)이 아니어서, 이런 기본 솔루션이 선택적이며 추가나 제거가 용이하다.
> ### 모놀리식 아키텍처(Monolithic Architecture)
> 모놀리식 아키텍처란, 마이크로서비스 아키텍처의 반대되는 개념으로 전통의 아키텍처를 지칭하는 의미로 생겨난 단어이다. 
> 
> 하나의 서비스 또는 어플리케이션이 하나의 거대한 아키텍처를 가질 때, 모놀리식(Monolithic)하다고 한다.
* 쿠버네티스는 분산 시스템을 탄력적으로 실행하기 위한 프레임 워크를 제공한다. 
* 애플리케이션의 확장과 장애 조치를 처리하고, 배포 패턴 등을 제공한다. 예를 들어, 쿠버네티스는 시스템의 카나리아 배포를 쉽게 관리 할 수 있다.
https://kubernetes.io/ko/docs/concepts/overview/what-is-kubernetes/

> ### 카나리(Canary)
> 카나리아 배포는 위험을 빠르게 감지할 수 있는 배포 전략이다.
> 
> 지정한 서버 또는 특정 user에게만 배포했다가 정상적이면 전체를 배포한다.
> 
> 서버의 트래픽의 일부를 신 버전으로 분산하여 오류 여부를 확인할 수 있다.
> 
> 이런 전략은 A/B 테스트가 가능하며, 성능 모니터링에 유용하다.
> 
> 트래픽을 분산시킬 때는 라우팅을 랜덤하게 할 수 있고, 사용자로 분류할 수도 있다.
> 
> <img width="783" alt="스크린샷 2022-06-27 오후 10 03 56" src="https://user-images.githubusercontent.com/82895809/175948236-8267bce4-1d44-473f-a448-9ba6bb6220ee.png">

## 쿠버네티스 아키텍처
도커가 컨테이너와 관련된 전반적이고 추상화된 기술을 제공한다고 하면, 쿠버네티스는 다량의 컨테이너를 하나의 물리적인 서버에 배치하여 이를 사용하듯이 관리해주는 역할을 한다.

쿠버네티스의 영역은 크게 노드를 하나로 묶는 클러스터를 관리하는 컨트롤 플레인 영역과 흩어져 있는 각 노드를 관리하는 노드 영역으로 나눌 수 있다.

### 클러스터

쿠버네티스 아키텍처에서 클러스터(Cluster)란 컨테이너 형태의 애플리케이션을 호스팅하는 물리/가상 환경의 노드들로 이루어진 집합을 의미한다.

온라인 인프라 영역에서의 컨테이너가 화물선에 선적되는 실물 컨테이너 개념에 비유된다면, 쿠버네티스 클러스터는 컨테이너들을 실은 컨테이너선과 이를 관리하는 통제함으로 이루어진 선단의 개념에 비유할 수 있는 것이다.

쿠버네티스 클러스터는 용도에 따라 크게 워커 노드(Worker Node)와 마스터 노드(Master Node)로 구분된다.

* 워커 노드(Worker Node) : 각기 다른 컨테이너들이 선적된 컨테이너선의 역할이다. 각기 다른 목적과 기능으로 세분화된 컨테이너들이 실제 배치되는 노드를 의미한다.

* 마스터 노드(Master Node) : 컨테이너 선단을 지휘하는 통제함의 역할이다. 대규모의 컨테이너를 운영하려면 각 워커 노드들의 가용 리소스 현황을 고려하여 최적의 컨테이너 배치와 모니터링, 그리고 각 컨테이너에 대한 효율적인 추적 관리가 필요해진다. 쿠버네티스 클러스터에서 이 역할을 수행하는 노드를 마스터 노드라 칭한다.

쿠버네티스를 배포하면 클러스터를 얻는다.

쿠버네티스 클러스터는 컨테이너화된 애플리케이션을 실행하는 노드라고 하는 워커 머신의 집합. 모든 클러스터는 최소 한 개의 워커 노드를 가진다.

워커 노드는 애플리케이션의 구성요소인 파드를 호스트한다. 컨트롤 플레인은 워커 노드와 클러스터 내 파드를 관리한다. 프로덕션 환경에서는 일반적으로 컨트롤 플레인이 여러 컴퓨터에 걸쳐 실행되고, 클러스터는 일반적으로 여러 노드를 실행하므로 내결함성과 고가용성이 제공된다.

* 내결함성(Fault Tolerance) : 시스템의 일부 구성 요소가 작동하지 않더라도 계속 작동할 수 있는 기능
* 고가용성(High Availability) : 서버와 네트워크, 프로그램 등의 정보 시스템이 상당히 오랜 기간 동안 지속적으로 정상 운영이 가능한 성질

<img width="796" alt="스크린샷 2022-06-27 오후 10 24 34" src="https://user-images.githubusercontent.com/82895809/175952450-041532f3-266f-4623-b52f-1cc44837bc1f.png">

### 컨트롤 플레인 컴포넌트
컨트롤 플레인 컴포너트는 클러스터에 관한 결정을 내리고 클러스터 이벤트를 감지하며 반응한다.

컨트롤 플레인 컴포넌트는 클러스터 내 어떠한 머신에서라도 동작할 수 있다.

* kube-apiserver : 쿠버네티스의 모든 컴포넌트의 통신을 담당.
* etcd : 모든 클러스터 데이터를 담는 쿠버네티스 뒷단의 저장소로 사용되는 일관성·고가용성 키-값 저장소
* kibe-scheduler : 노드가 배정되지 않은 새로 생성된 파드 를 감지하고, 실행할 노드를 선택하는 컨트롤 플레인 컴포넌트.
* kube-controller-manager : 컨트롤러 프로세스를 실행하는 컨트롤 플레인 컴포넌트.
<img width="616" alt="스크린샷 2022-06-27 오후 10 31 22" src="https://user-images.githubusercontent.com/82895809/175953794-16b20976-810c-48d0-8f19-1d4f7a49e4b9.png">

* cloud-controller-manager : 클라우드별 컨트롤 로직을 포함하는 쿠버네티스 컨트롤 플레인 컴포넌트. 클라우드 컨트롤러 매니저를 통해 클러스터를 클라우드 공급자의 API에 연결하고, 해당 클라우드 플랫폼과 상호 작용하는 컴포넌트와 클러스터와만 상호 작용하는 컴포넌트를 구분할 수 있게 해줌.
* node-component : 노드 컴포넌트는 동작 중인 파드를 유지시키고 쿠버네티스 런타임 환경을 제공하며, 모든 노드 상에서 동작.
* kubelet : 클러스터의 각 노드에서 실행되는 에이전트. Kubelet은 파드에서 컨테이너가 확실하게 동작하도록 관리.
* kube-proxy : kube-proxy는 클러스터의 각 노드에서 실행되는 네트워크 프록시로, 쿠버네티스의 서비스 개념의 구현부.
* container-runtime : 컨테이너 런타임은 컨테이너 실행을 담당하는 소프트웨어. 쿠버네티스는 containerd, CRI-O와 같은 컨테이너 런타임 및 모든 Kubernetes CRI (컨테이너 런타임 인터페이스) 구현체를 지원.

## 쿠버네티스 설치
토큰 재발급 (토큰은 24시간의 제한 시간 있음)
```
kubeadm token create —print-join-command
```

### 파드 네트워크 Add-On 설치

* pod란?
  * 쿠버네티스의 실행 최소 단위
  * 쿠버네티스에서는, 프로세스를 실행할 때 “컨테이너를 사용하지 않고”, “Pod라는 리소스를 사용”한다.

* STATUS : 해당 컨테이너(pod)가 현재 동작중인 상태
  * Pending : 생성 명령 O, 실행은 X
  * ContainerCreating : 생성 중
  * Running : 정상 실행 중
  * Completed : 한번 실행하고 완료된 상태
  * Error : 에러
  * CrashLoopBackOff : 지속적인 에러 상태로 있어 crash가 반복 중

통신을 하기 위해서는 Pod Network Add-On을 설치해야 한다.

파드들의 배포와 통신을 위해 네트워크를 설정해야 하기 때문이다.

만약 이 과정을 거치지 않는다면 파드 배포가 불가능해진다.

각 클러스터마다 Network Add-On 하나씩만 설치할 수 있다.

<img width="1111" alt="스크린샷 2022-06-28 오전 12 17 20" src="https://user-images.githubusercontent.com/82895809/175975195-da9e19c0-2f49-4df1-a4f2-336a00c0b73f.png">

설치된 쿠버네티스의 상태를 볼 수 있다.
```
kubectl get nodes -o wide
```
Ready 상태가 되면 정상적으로 설치가 된 것이다.

### 포트 추가
쿠버네티스를 운영하기 위해서는 필요한 포트를 열어 주어야 한다.

<img width="707" alt="스크린샷 2022-06-28 오전 12 26 26" src="https://user-images.githubusercontent.com/82895809/175976764-b0d19ed0-6bf8-4b27-bc42-0545fd5608f0.png">

## 쿠버네티스의 오브젝트
쿠버네티스는 모든 요소를 yaml 파일로 생성하여 관리할 수 있다.

복잡한 인프라 관리에 코드화가 보편화된 최근 설정 파일을 사용하면 기존의 쿠버네티스 관리가 훨씬 수월할 것이다.

### 네임스페이스
네임스페이스는 하나의 물리적인 공간에 있는 쿠버네티스를 다수의 팀이 사용할 떄 유용하다.

```
kubectl get namespace
```
namespace.yaml 파일을 만든다.
```
vi namespace.yml
```

```
apiVersion: v1
kind: Namespace
metadata:
        name: team1
```

<img width="582" alt="스크린샷 2022-06-28 오전 12 58 37" src="https://user-images.githubusercontent.com/82895809/175982836-20c1bed1-234b-4e0f-a7a1-8c6119b7f73c.png">

파드 생성 확인
```
kubectl get pod
```

<img width="405" alt="스크린샷 2022-06-28 오전 1 03 51" src="https://user-images.githubusercontent.com/82895809/175984002-ae465170-b74e-4c1b-ba3a-00c6db4485a2.png">

-n 옵션을 사용하지 않고 조회하면 -n default라는 옵션을 사용한 것과 동일하기 때문에 파드 목록이 보이지 않는다.

삭제
```
kubectl delete pod night -n team1
kubectl delete namespace team1
```
<img width="457" alt="스크린샷 2022-06-28 오전 1 05 26" src="https://user-images.githubusercontent.com/82895809/175984656-9132dc4f-cd54-4314-8dd9-386057513ed2.png">

### 파드
파드는 쿠버네티스의 구성 요소 중 가장 작은 단위의 객체.

파드는 해당 클러스터에서 러닝 프로세스를 나타낸다.

파드는 쿠버네티스에서 컨테이너와 같다고 보면 된다.

쿠버네티스에서는 파드 내에 여러 개의 컨테이너 존재 가능.

파드의 공유 컨텍스트는 Linux 네임스페이스, 컨트롤 그룹(cgroup) 및 도커 컨테이너를 격리하는 것과 같이 잠재적으로 서로 다른 격리 요소들이다.

파드 안의 컨테이너들은 IP 주소와 포트 공간을 공유하고, localhost를 통해 서로를 찾을 수 있다.


### 노드
쿠버네티스는 컨테이너를 파드내에 배치하고 노드 에서 실행함으로 워크로드를 구동한다. 

노드는 클러스터에 따라 가상 또는 물리적 머신일 수 있다. 

각 노드는 컨트롤 플레인에 의해 관리되며 파드를 실행하는 데 필요한 서비스를 포함한다.

** 생명주기는 위에 **

#### 파드 생성
```
apiVersion: v1
kind: Pod
metadata:
        name: nodejs-app
spec:
        containers:
                - name: nodejs-app
                  image: uphiller/nodejs-hello-world
                  ports:
                          - containerPort: 3000
```

생성 확인
```
kubectl apply -f pod.yaml 
kubectl get pod
```

<img width="384" alt="스크린샷 2022-06-28 오전 1 21 06" src="https://user-images.githubusercontent.com/82895809/175988492-064764b0-76a1-4e74-a8a6-55924902c916.png">

Running 상태어야 하는데 자꾸 나는 Pending 뜸 나중에 해보겠음....

기존 nodejs 컨테이너에 mysql 컨테이너 포함하는 파드

```
apiVersion: v1
kind: Pod
metadata:
  name: nodejs-app
spec:
  containers:
  - name: nodejs-app
    image: heroku/nodejs-hello-world
    ports:
    - containerPort: 3000
  - name: mysql-app
    image: mysql
    env:
    - name: MYSQL_ROOT_PASSWORD
      value: "1234"
    ports:
    - containerPort: 3306
```

Ready 부분에서 2가 표시되어야 함.

```
kubectl describe pod nodejs-app
```

<img width="867" alt="스크린샷 2022-06-28 오전 1 28 38" src="https://user-images.githubusercontent.com/82895809/175990978-7cb11cce-5981-4feb-afba-875337adbb31.png">


구성 파일에 설정한 mysql 패스워드도 잘 설정된 것을 확인할 수 있다.

### 디플로이먼트
파드와 레플리카셋에 대한 선언과 업데이트를 제공하는 상위 개념의 컨트롤러.

서비스를 파드만으로 운영하면 복제 불가.

디플로이먼트는 파드의 복제뿐만 아니라 여러 부분을 조절하도록 해 준다.

디플로이먼트 생성 (nodejs.yaml)

```
# vi nodejs.yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  name: nodejs-app
  labels:
    app: nodejs-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: nodejs-app
  template:
    metadata:
      labels:
        app: nodejs-app
    spec:
      containers:
      - name: nodejs-app
        image: uphiller/nodejs-hello-world
        ports:
        - containerPort: 3000
```

```
kubectl apply -f nodejs.yaml 

kubectl get pod

kubectl get replicaset

kubectl get deployment
```

<img width="526" alt="스크린샷 2022-06-28 오전 1 33 55" src="https://user-images.githubusercontent.com/82895809/175991003-2c44b1f1-374c-4058-b985-4e68360edc73.png">

파드, 레를리카셋, 디플로이먼트가 모두 생성된 것을 확인.

복제 개수를 늘리면 구성 파일에서 replicas: 1의 숫자를 늘리면 된다.

2로 늘린후 실행.

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nodejs-app
  labels:
    app: nodejs-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: nodejs-app
  template:
    metadata:
      labels:
        app: nodejs-app
    spec:
      containers:
      - name: nodejs-app
        image: uphiller/nodejs-hello-world
        ports:
        - containerPort: 3000
```

```
kubectl apply -f nodejs.yaml
```

출력 부분이 created -> configured 로 바뀐 것은 설정만 바꿔서 실행하였기 때문.


<img width="493" alt="스크린샷 2022-06-28 오전 1 39 52" src="https://user-images.githubusercontent.com/82895809/175991783-3f3374a4-ffc8-4489-b03d-0609f4446a92.png">

그리고 다시 kubectl get ....을 하면

레플리카, 파드, 디플로이먼트 다 2개가 생성된 것을 확인할 수 있다.

```
      containers:
      - name: nodejs-app
        image: uphiller/nodejs-hello-world:1.0
```

컨테이너 이미지 부븐의 TAG를 수정하고 다시 실행.

업데이트가 잘 되었는지 확인하기 위해서는 롤아웃 명령어를 사용해야 함.

```
kubectl rollout status deployment.apps/nodejs-app
```

롤아웃 명령어를 입력하고 성공 상태를 확인한다.

<img width="819" alt="스크린샷 2022-06-28 오전 1 43 47" src="https://user-images.githubusercontent.com/82895809/175992899-24de7ca5-9ec1-4b67-a5c1-b46ad1ca3ebb.png">


(나는 pending이라 여기서 멈춘 듯함)

```
kubectl rollout history deployment.apps/nodejs-app
```

위의 명령어로 디플로이먼트의 변경 히스토리를 볼 수 있다.

<img width="602" alt="스크린샷 2022-06-28 오전 1 45 45" src="https://user-images.githubusercontent.com/82895809/175992935-79e7b7fd-5f9c-4040-a1dc-24981f3bb81a.png">

변경 히스토리는 이미지가 바뀌었을 때만 기록된다.

그리고 이미지가 바뀌었을 때 파드의 변경 상태를 보면 이전의 이미지로 생성된 파드는 삭제되고 새로운 이미지의 파드가 생성된다.

<img width="541" alt="스크린샷 2022-06-28 오전 1 46 59" src="https://user-images.githubusercontent.com/82895809/175993218-71bfb301-3a21-472b-bad4-22e51c5daf96.png">

이처럼 파드는 언제든지 생성되고 없어지기 때문에 파드 오브젝트 자체를 컨트롤하지 않고 디플로이먼트와 같은 컨트롤러를 사용하는 것.

이때 CHANGE-CAUSE가 none인 이유는 CHANGE-CAUSE를 지정하는 명령을 실행하지 않았기 떄문이다.

업데이트 명령을 실행 후, 현재 리비전의 CHANGE-CAUSE를 지정하는 명령을 실행하면 된다.

```
kubectl annotate deployment.apps/nodejs-app kubernetes.io/change="image updated to latest"

kubectl rollout history deployment.apps/nodejs-app
````
지속적인 충돌로 인해 디플로이먼트가 안정적이지 않은 경우도 기본적으로 모든 디플로이먼트의 롤아웃 기록은 시스템에 남아 있기 때문에 언제든지 롤백이 가능하다.

업데이트 후 롤백을 해보자.

```
# nodejs.yaml

      containers:
      - name: nodejs-app
        image: uphiller/nodejs-hello-world:1.88
```
이제 다시 바꾼 설정을 적용하고

```
kubectl rollout status deployment.apps/nodejs-app
```
을 하면

<img width="815" alt="스크린샷 2022-06-28 오전 1 52 46" src="https://user-images.githubusercontent.com/82895809/175994654-1726923f-064d-42ee-b7b8-6f7976d7b162.png">


이미지의 태그가 존재하지 않는 태그여서 업데이트가 진행되지 않는다. (이게 정상)

```
kubectl get rs
kubectl get pods
```
위의 명령어를 입력하고 파드를 보면 이미지풀에 에러가 난 것을 확인 가능.

에러가 발생하면 디플로이먼트 컨트롤러가 새로운 레플리케이션의 스케일업을 중지한다.

```
kubectl describe deployment
```

디플로이먼트에 대한 정보를 보면 더 자세히 알 수 있다.

<img width="944" alt="스크린샷 2022-06-28 오전 1 57 04" src="https://user-images.githubusercontent.com/82895809/175995167-fe3c5d53-522a-4504-be12-f08ff01873cf.png">

롤백을 해보자.

```
kubectl rollout undo deployment.apps/nodejs-app
```
undo명령을 실행하면 롤백이 된다.

그 다음 status로 확인 하면 정상적으로 롤백이 된 것을 확인 가능하다.

특정 리비전으로 롤백 하고 싶을 때는 리비전을 명시한 undo명령을 실행하면 된다.

```
kubectl rollout undo deployment.apps/nodejs-app --to-revision=2
```
