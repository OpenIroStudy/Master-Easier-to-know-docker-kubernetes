
### Selinux 설정
``` ruby
$ vi /etc/sysconfig/selinux  
```  
SELINUX=disabled을SELINUX=permissive로 변경후 저장.위 설정은 서버를 reboot 해야 적용.  

### 방화벽 해제
``` ruby
systemctl stop firewalld && systemctl disable firewalld

systemctl stop NetworkManager && systemctl disable NetworkManager
```  

### SWAP 비활성화
``` ruby
swapoff -a && sed -i '/ swap / s/^/#/' /etc/fstab
```  

### Iptables 커널 옵션 활성화
``` ruby 
cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sysctl --system
```  

### 쿠버네티스 YUM Repository 설정
``` ruby
cd /etc/yum.repos.d   
vi kubernetes.repo
  
[kubernetes]
name=Kubernetes
baseurl=https://packages.cloud.google.com/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=1
repo_gpgcheck=0
gpgkey=https://packages.cloud.google.com/yum/doc/rpm-package-key.gpg  
  
yum update 
```  


https://sangchul.kr/77

# centos 설치 !!!!!!
https://zunoxi.tistory.com/42?category=950191


## 방화벽
전부 내리지 말고 필요한 포트만 허용한다.  
![image](https://user-images.githubusercontent.com/67637716/176613629-dee5630b-c4d1-4242-9c6f-201fcf74d920.png)  


## 네트워크 설정
 kubectl apply -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"
 
 
 kubectl apply -f https://github.com/coreos/flannel/raw/master/Documentation/kube-flannel.yml

