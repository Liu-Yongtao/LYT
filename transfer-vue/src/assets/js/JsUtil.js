
export default{
    /**
* @function 防抖函数，规定时间内点击多次，只执行最后一次
* @method debounce(fn,delay);
* @param {function} fn       需要执行的函数
* @param {number} [delay=300]     等待时间（毫秒）,默认等待时间为300毫秒,为0的话就不需要防抖函数了
* @example 
*/
  debounce(fn, delay = 500){
    let timer = null; // 创建一个标记用来存放定时器的返回值
    return function (...args) {
      clearTimeout(timer);
      timer = setTimeout(() => {
        fn.apply(this, arguments);
      }, delay);
    };
},

/**
* @function 节流函数，连续点击多次，规定时间内只执行一次
* @method throttle(fn,delay);
* @param {function} fn       需要执行的函数
* @param {number} [delay=300]     等待时间（毫秒）,默认等待时间为300毫秒
* @example 
*/
  throttle(fn, delay = 300){
    let timer = null; // 创建一个标记用来存放定时器的返回值
    return function () {
    
      if (!timer) {

        timer = setTimeout(() => {
          console.log("12313");
          fn.apply(this, arguments);    //或者使用fn()
          timer = null;
        }, delay);
      }
    };
}


}
