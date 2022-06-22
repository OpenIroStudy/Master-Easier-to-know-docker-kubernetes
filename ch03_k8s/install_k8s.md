## error 발생 

### $ sudo kubeadm init (p117)  

```
[init] Using Kubernetes version: v1.24.2
[preflight] Running pre-flight checks
        [WARNING SystemVerification]: missing optional cgroups: hugetlb
error execution phase preflight: [preflight] Some fatal errors occurred:
        [ERROR CRI]: container runtime is not running: output: E0622 13:44:13.038673   21979 remote_runtime.go:925] "Status from runtime service failed" err="rpc error: code = Unimplemented desc = unknown service runtime.v1alpha2.RuntimeService"
time="2022-06-22T13:44:13Z" level=fatal msg="getting status of runtime: rpc error: code = Unimplemented desc = unknown service runtime.v1alpha2.RuntimeService"
, error: exit status 1
[preflight] If you know what you are doing, you can make a check non-fatal with `--ignore-preflight-errors=...`
To see the stack trace of this error execute with --v=5 or higher
```

#### 해결방법 
$ sudo vi /etc/containerd/config.toml   

-> disabled_plugins 항목에서 CRI 제거한 뒤   

$ systemctl restart containerd   

* 개인기록 
   * linux pw: 78972

--- 

### $ kubectl apply -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"  (p119)
```
The connection to the server localhost:8080 was refused - did you specify the right host or port?
```

#### 해결방법 

https://twofootdog.tistory.com/82      

2)kubeadmin으로 구축한 클러스터에 접근할 때 발생한 경우로 해결.    

--- 

### kubectl get pods (p121)
pod status pending issue   

#### 해결방법 
$ kubectl taint nodes --all node-role.kubernetes.io/control-plane   

에러원인은 `node-role.kubernetes.io/master taint is deprecated and kubeadm will stop using it in version 1.25.` 공식문서에 있음......... 하 열받네 ^^    

https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/




---

## token 값 
kubeadm join 10.178.0.2:6443 --token a8iosg.wmh48364nuifhn1z \
--discovery-token-ca-cert-hash sha256:d69b8214a4b89091ac9979fb246e46c92a161f900a8e03b0e5ddb11b09b2a76f 
