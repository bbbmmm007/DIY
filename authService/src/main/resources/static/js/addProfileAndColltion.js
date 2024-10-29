
window.addToCollection = function (productId) {
    $.ajax({
        url: '/users/add_collection/'+productId,
        type: 'POST',
        success: function (json) {
            if (json.state === 200) {
                alert('商品已加入收藏');
                collectionIds.add(productId); // 更新收藏状态
                localStorage.setItem('collectionIds', JSON.stringify(Array.from(collectionIds))); // 保存到 localStorage
                updateProductRow(productId); // 更新商品行
            } else {
                alert('加入收藏失败: ' + json.message);
            }
        },
        error: function () {
            alert('加入收藏失败，服务器错误');
        }
    });
};

// 加入购物车
window.addToCart = function (productId) {
    $.ajax({
        url: '/users/add_carts/' + productId, // 将 productId 作为路径参数
        type: 'POST',
        success: function (json) {
            if (json.state === 200) {
                alert('商品已加入购物车');
                cartIds.add(productId); // 更新购物车状态
                updateProductRow(productId); // 更新商品行
            } else {
                alert('加入购物车失败: ' + json.message);
            }
        },
        error: function () {
            alert('加入购物车失败，服务器错误');
        }
    });
};
