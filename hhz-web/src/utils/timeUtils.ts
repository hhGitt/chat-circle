// 休眠
export async function showMessage(ms: number) {
    console.log('开始等待...');
    await sleep(ms)
    console.log('等待结束！');
}

function sleep(ms: number): Promise<void> {
    return new Promise((resolve) => setTimeout(resolve, ms))
}

