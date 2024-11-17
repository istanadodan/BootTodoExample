# 파일: StartTunnel.ps1
$env:KUBECONFIG = "$HOME\.kube\config"
minikube tunnel --cleanup | Out-Null
Start-Sleep -Seconds 5
kubectl port-forward service/database 3336:3306