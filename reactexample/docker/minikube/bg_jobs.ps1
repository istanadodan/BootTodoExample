param(
    [string]$name = ""
)

function Start-MountJob {
    Start-Job -ScriptBlock {
        minikube mount D:\works\react-project\BootTodoExample\build\libs:/mnt/libs
    } -Name BE-volume | Out-Null
    Start-Job -ScriptBlock {
        minikube mount D:\works\react-project\reactexample\build:/mnt/app
    } -Name FE-volume | Out-Null
    Write-Host "Mounting job started."
}

function Start-ServiceJob {
    # Start-Job -ScriptBlock {
    #     kubectl port-forward service/backend 8080:8080
    # } -Name BS-service | Out-Null
    # Start-Job -ScriptBlock {
    #     kubectl port-forward service/frontend 3000:3000
    # } -Name FE-service | Out-Null

    Start-Job -ScriptBlock {
        minikube tunnel
    } -Name Tunneling | Out-Null    
    
    Write-Host "Service port-forward job started."
}

# function Show-Dashboard {
#     $job = Start-Job -ScriptBlock {
#         $dashboardUrl = minikube dashboard --url
#         while (-not $dashboardUrl) {
#             Start-Sleep -Seconds 1
#             $dashboardUrl = minikube dashboard --url
#         }
#         Start-Process $dashboardUrl
#     } -Name Dashboard
#     Write-Host $job
# }

if ($name -eq "" -or $name -eq "mnt") {
    Start-MountJob
}

if ($name -eq "" -or $name -eq "svc") {
    Start-ServiceJob
}

# IF ($NAME -eq "dashboard") {
#     Show-Dashboard
# }

if ($name -ne "" -and $name -ne "mnt" -and $name -ne "svc" -and $name -ne "dashboard") {
    Write-Host "Invalid name parameter. Use 'mnt', 'svc', or leave blank for both operations."
}


kubectl rollout restart deploy
Write-Host "deploys have restarted"