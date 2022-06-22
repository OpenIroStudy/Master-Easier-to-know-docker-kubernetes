``` ruby
sudo apt-get update

sudo apt-get install -y ca-certificates curl gnupg lsb-release software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

sudo apt-get update

sudo apt-get install -y docker-ce docker-ce-cli containerd.io

sudo usermod -aG docker $USER

sudo mkdir /etc/docker
cat <<EOF | sudo tee /etc/docker/daemon.json
{
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "100m"
  },
  "storage-driver": "overlay2"
}
EOF

sudo systemctl enable docker

sudo systemctl daemon-reload

sudo systemctl restart docker

curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -

cat <<EOF | sudo tee /etc/apt/sources.list.d/kubernetes.list
deb https://apt.kubernetes.io/ kubernetes-xenial main
EOF

sudo apt-get update

sudo apt-get install -y kubelet kubeadm kubectl

sudo apt-mark hold kubelet kubeadm kubectl
```  

``` ruby
sudo kubeadm init

///// 밑에 코드 복사
kubeadm join 10.178.0.7:6443 --token 55hgzc.ej223swrgtl1isje \
--discovery-token-ca-cert-hash sha256:2b1e8c917919e0a554bcf5e28a2e8000032e22b3b73dedf896aeda4e23c5890b
```  

### init 에러시
[ERROR CRI]: container runtime is not running: output:  

위와 같은 에러시  
``` ruby
rm /etc/containerd/config.toml
systemctl restart containerd
kubeadm init
```  

``` ruby
mkdir -p $HOME/.kube

sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config

sudo chown $(id -u):$(id -g) $HOME/.kube/config

kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

#마스터노드 파드설치 허용
kubectl taint nodes --all node-role.kubernetes.io/master-

kubectl apply -f "https://cloud.weave.works/k8s/scope.yaml?k8s-service-type=NodePort" 
```  

# 파드 네트워크 add-on 설치
``` ruby
kubectl apply -n kube-system -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"

watch kubectl get pods --all-namespaces


```
