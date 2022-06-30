
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
