#include <stdlib.h>
#include <stdio.h>
#include "extmem.h"

int main(int argc, char **argv)
{
    Buffer buf; /* A buffer */
    unsigned char *blk; /* A pointer to a block */

    /* Initialize the buffer */
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    /* Get a new block in the buffer */
    blk = getNewBlockInBuffer(&buf);

    /* Fill data into the block */
    for (int i = 0; i < 8; i++)
        *(blk + i) = 'a' + i;

    /* Write the block to the hard disk */
    if (writeBlockToDisk(blk, 8888, &buf) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }

    /* Read the block from the hard disk */
    if ((blk = readBlockFromDisk(1, &buf)) == NULL)
    {
        perror("Reading Block Failed!\n");
        return -1;
    }

    freeBlockInBuffer(blk,&buf);

    /* Process the data in the block */
    /**
    int X = -1;
    int Y = -1;
    int addr = -1;

    char str[5];
    printf("block:1\n");
    for (i = 0; i < 7; i++) //一个blk存7个元组加一个地址
    {

        for (int k = 0; k < 4; k++)
        {
            str[k] = *(blk + i*8 + k);
        }
        X = atoi(str);
        for (int k = 0; k < 4; k++)
        {
            str[k] = *(blk + i*8 + 4 + k);
        }
        Y = atoi(str);
        printf("(%d, %d) ", X, Y);
    }
    for (int k = 0; k < 4; k++)
    {
        str[k] = *(blk + i*8 + k);
    }
    addr = atoi(str);
    printf("\nnext address = %d \n", addr);


    printf("\n");
    printf("IO's is %d\n", buf.numIO);
    */
    /* Check the number of IO's */

    /**
    1 结果放在101-102
    实现基于线性搜索的关系选择算法：基于 ExtMem 程序库，使用 C 语言
实现线性搜索算法，选出 S.C=128 的元组，记录 IO 读写次数，并将选择
结果存放在磁盘上。（模拟实现 select S.C, S.D from S where S.C = 128）
    */
    //R
    int RBEGIN = 1;int REND = 16;
    //S
    int SBEGIN = 17;int SEND = 48;
    //R
    int A,B;
    //S
    int C,D;

    char str[8];
    int count = 0;

    printf("********************(1)********************\n");

    //用以写文件
    unsigned char *blk_write;
    blk_write = getNewBlockInBuffer(&buf);

    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }
    int currentGroup = 1;
    for(int num = SBEGIN; num <= SEND; num++)
    {
        printf("读入数据块%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int i = 0; i < 7; i++) //一个blk存7个元组加一个地址
        {
            for (int k = 0; k < 4; k++)
            {
                str[k] = *(blk + i * 8 + k);
            }
            C = atoi(str);
            for (int k = 0; k < 4; k++)
            {
                str[k] = *(blk + i * 8 + 4 + k);
            }
            D = atoi(str);
            if(C == 128)
            {
                if(count == 8)
                {
                    count = 0;
                    printf("写入数据块%d\n",100 + currentGroup);
                    if (writeBlockToDisk(blk_write, 100 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    currentGroup++;
                    blk_write = getNewBlockInBuffer(&buf);
                }
                printf("(C = %d, D = %d)\n", C, D);
                for (int k = 0; k < 8; k++)
                {
                    *(blk_write + count * 8 + k) = *(blk + i * 8 + k);
                }
                count++;
            }
        }
        freeBlockInBuffer(blk,&buf);
    }
    printf("写入数据块%d\n",100 + currentGroup);
    if (writeBlockToDisk(blk_write, 100 + currentGroup, &buf) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    currentGroup++;

    printf("(1)读写IO次数:%ld\n", buf.numIO);

    /**
    2 子集排序结果在201-248 总排序结果在301-348
    实现两阶段多路归并排序算法（TPMMS）：利用内存缓冲区将关系 R
和 S 分别排序，并将排序后的结果存放在磁盘上。
    */

    printf("********************(2)********************\n");
    printf("*************划分子集并子集排序*************\n");

    /* 对R的A进行排序*/

    for(int num = RBEGIN; num <= REND; num++)
    {
        printf("读入数据块%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int i = 0; i < 7; i++) //一个blk存7个元组加一个地址
        {
            int minA = 1000;
            int minAcount = i;
            //找到最大的值
            for(int j = i; j < 7; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    str[k] = *(blk + j * 8 + k);
                }
                int currentA = atoi(str);

                if(currentA < minA)
                {
                    minA = currentA;
                    minAcount = j;
                }
            }
            //交换最大的值和第i个值
            char temp[8];
            for (int k = 0; k < 8; k++)
            {
                temp[k] = *(blk + i * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + i * 8 + k) = *(blk + minAcount * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + minAcount * 8 + k) = temp[k];
            }
            //写地址
            sprintf(str, "%d ", 201 + num);
            for (int k = 0; k < 4; k++)
            {
                *(blk + 56 + k) = str[k];
            }
        }
        //写到100 + num处
        printf("写入数据块%d\n",200 + num);
        if (writeBlockToDisk(blk, 200 + num, &buf) != 0)
        {
            perror("Writing Block Failed!\n");
            return -1;
        }
    }

    /* 对S的C进行排序*/

    for(int num = SBEGIN; num <= SEND; num++)
    {
        printf("读入数据块%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int i = 0; i < 7; i++) //一个blk存7个元组加一个地址
        {
            int minC = 1000;
            int minCcount = i;
            //找到最大的值
            for(int j = i; j < 7; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    str[k] = *(blk + j * 8 + k);
                }
                int currentC = atoi(str);

                if(currentC < minC)
                {
                    minC = currentC;
                    minCcount = j;
                }
            }
            //交换最大的值和第i个值
            char temp[8];
            for (int k = 0; k < 8; k++)
            {
                temp[k] = *(blk + i * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + i * 8 + k) = *(blk + minCcount * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + minCcount * 8 + k) = temp[k];
            }
            //写地址
            sprintf(str, "%d ", 101 + num);
            for (int k = 0; k < 4; k++)
            {
                *(blk + 56 + k) = str[k];
            }
        }
        //写到100 + num处
        printf("写入数据块%d\n",200 + num);
        if (writeBlockToDisk(blk, 200 + num, &buf) != 0)
        {
            perror("Writing Block Failed!\n");
            return -1;
        }
    }

    int max = 48;
    int useCount[max + 1];//用以记录每个磁盘数据块用到第几块数据
    for(int i = 0; i < max + 1; i++)
    {
        useCount[i] = 0;
    }

    printf("**************各子集间归并排序**************\n");

    /* 对R的A进行归并排序 */

    unsigned char *blk_plus[4];
    for(int i = 0; i < 4; i++)
    {
        blk_plus[i] = getNewBlockInBuffer(&buf);
    }
    unsigned char *blk_get;
    //初始化缓冲区
    count = 0;
    for(int num = RBEGIN; num <= REND; num++)
    {
        if ((blk_get = readBlockFromDisk(num + 200, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 8; k++)
        {
            blk_plus[count / 64][count % 64 + k] = *(blk_get + k);
        }
        count += 8;
        useCount[num]++;
        freeBlockInBuffer(blk_get,&buf);
    }
    int current = 0;
    currentGroup = 1;
    blk_write = getNewBlockInBuffer(&buf);
    for(int num = 0; num < 112; num++)
    {
        int minA = 999;
        int minACount = 0;
        //找出缓冲区最小数据
        for(count = 0; count < 128; count += 8)
        {
            //if(useCount[(count / 4) + 1] < 8)
            for (int k = 0; k < 4; k++)
            {
                str[k] = blk_plus[count / 64][count % 64 + k];
            }
            A = atoi(str);
            if(A < minA)
            {
                minA = A;
                minACount = count;
            }
        }
        //将缓冲区最小数据写入blk
        for (int k = 0; k < 8; k++)
        {
            *(blk_write + current + k) = blk_plus[minACount / 64][minACount % 64 + k];
        }
        //更新被写入的最小数据
        if(useCount[(minACount / 8) + RBEGIN] <= 6)
        {
            //如果还有数据可取
            if ((blk_get = readBlockFromDisk(((minACount / 8) + RBEGIN) + 200, &buf)) == NULL)
            {
                perror("%dReading Block Failed!\n");
                return -1;
            }
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minACount / 64][minACount % 64 + k] = *(blk_get + useCount[(minACount / 8) + RBEGIN] * 8 + k);
            }
            freeBlockInBuffer(blk_get,&buf);
        }
        else
        {
            //如果没有数据可取
            sprintf(str, "%d %d ", 999, 999);
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minACount / 64][minACount % 64 + k] = str[k];
            }
        }
        useCount[(minACount / 8) + RBEGIN]++;
        current += 8;
        if(current == 56)
        {
            //写地址
            sprintf(str, "%d ", 301 + currentGroup);
            for (int k = 0; k < 4; k++)
            {
                *(blk_write + current + k) = str[k];
            }
            //将数据块写入
            printf("写入数据块%d\n",300 + currentGroup);
            if (writeBlockToDisk(blk_write, 300 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            currentGroup++;
            current = 0;
            //从缓冲区获得新数据块
            blk_write = getNewBlockInBuffer(&buf);
        }
    }
    /* 对S的C进行归并排序 */

    //初始化缓冲区
    count = 0;
    for(int num = SBEGIN; num <= SEND; num++)
    {
        if ((blk_get = readBlockFromDisk(num + 200, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 8; k++)
        {
            blk_plus[count / 64][count % 64 + k] = *(blk_get + k);
        }
        count += 8;
        useCount[num]++;
        freeBlockInBuffer(blk_get,&buf);
    }
    blk_write = getNewBlockInBuffer(&buf);
    for(int num = 0; num < 224; num++)
    {
        int minC = 999;
        int minCCount = 0;
        //找出缓冲区最小数据
        for(count = 0; count < 256; count += 8)
        {
            //if(useCount[(count / 4) + 1] < 8)
            for (int k = 0; k < 4; k++)
            {
                str[k] = blk_plus[count / 64][count % 64 + k];
            }
            C = atoi(str);
            if(C < minC)
            {
                minC = C;
                minCCount = count;
            }
        }
        //将缓冲区最小数据写入blk
        for (int k = 0; k < 8; k++)
        {
            *(blk_write + current + k) = blk_plus[minCCount / 64][minCCount % 64 + k];
        }
        //更新被写入的最小数据
        if(useCount[(minCCount / 8) + SBEGIN] <= 6)
        {
            //如果还有数据可取
            if ((blk_get = readBlockFromDisk(((minCCount / 8) + SBEGIN) + 200, &buf)) == NULL)
            {
                perror("Reading Block Failed!\n");
                return -1;
            }
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minCCount / 64][minCCount % 64 + k] = *(blk_get + useCount[(minCCount / 8) + SBEGIN] * 8 + k);
            }
            freeBlockInBuffer(blk_get,&buf);
        }
        else
        {
            //如果没有数据可取
            sprintf(str, "%d %d ", 999, 999);
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minCCount / 64][minCCount % 64 + k] = str[k];
            }
        }
        useCount[(minCCount / 8) + SBEGIN]++;
        current += 8;
        if(current == 56)
        {
            //写地址
            sprintf(str, "%d ", 301 + currentGroup);
            for (int k = 0; k < 4; k++)
            {
                *(blk_write + current + k) = str[k];
            }
            //将数据块写入
            printf("写入数据块%d\n",300 + currentGroup);
            if (writeBlockToDisk(blk_write, 300 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            currentGroup++;
            current = 0;
            //从缓冲区获得新数据块
            blk_write = getNewBlockInBuffer(&buf);
        }
    }

    //清空缓冲区
    for(int i = 0; i < 4; i++)
    {
        freeBlockInBuffer(blk_plus[i],&buf);
    }
    freeBlockInBuffer(blk_get,&buf);
    freeBlockInBuffer(blk_write,&buf);
    freeBlockInBuffer(blk,&buf);



    /**
    3 R关于A索引块在351-352 S关于C索引块在353-356 结果写在121-122
    实现基于索引的关系选择算法：利用（2）中的排序结果为关系 S 建立索
引文件，利用索引文件选出 S.C=128 的元组，并将选择结果存放在磁盘
上。记录 IO 读写次数，与（1）中的结果对比。（模拟实现 select S.C, S.D
from S where S.C = 128 ）
    */
    printf("********************(3)********************\n");

    //为R.A设置索引块，每个索引块大小为64字节，每个索引包括A值大小(4字节)和对应块的位置(4字节)，每个索引8字节大小
    //假设每个索引可以索引R属性一整个数据块(1个索引对应7个数据)
    //则R.A需要16个索引，1个索引块可以存放64/8=8个索引，至少需要2个索引块，而S则至少需要4个索引块
    //设置351-352为R.A索引块
    //设置353-356为S.C索引块

    blk = getNewBlockInBuffer(&buf);
    current = 0;
    currentGroup = 1;
    for(int num = RBEGIN; num <= REND; num++)
    {
        //读入每个数据块第1位
        if ((blk_get = readBlockFromDisk(num + 300, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + k) = *(blk_get + k);
        }
        freeBlockInBuffer(blk_get,&buf);

        //写地址
        sprintf(str, "%d ", num + 300);
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + 4 + k) = str[k];
        }
        current += 8;
        //写入数据块
        if(current == 64)
        {
            current = 0;
            //将数据块写入
            printf("写入索引块%d\n",350 + currentGroup);
            if (writeBlockToDisk(blk, 350 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            blk = getNewBlockInBuffer(&buf);
            currentGroup++;
        }
    }

    blk = getNewBlockInBuffer(&buf);
    for(int num = SBEGIN; num <= SEND; num++)
    {
        //读入每个数据块第1位
        if ((blk_get = readBlockFromDisk(num + 300, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + k) = *(blk_get + k);
        }
        freeBlockInBuffer(blk_get,&buf);

        //写地址
        sprintf(str, "%d ", num + 300);
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + 4 + k) = str[k];
        }
        current += 8;
        //写入数据块
        if(current == 64)
        {
            current = 0;
            //将数据块写入
            printf("写入索引块%d\n",350 + currentGroup);
            if (writeBlockToDisk(blk, 350 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            blk = getNewBlockInBuffer(&buf);
            currentGroup++;
        }
    }
    freeBlockInBuffer(blk,&buf);
    //初始化缓冲区
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }
    //从353-356索引块中寻找
    int find = 0;
    int beginaddr = 317;
    int endaddr = 348;
    for(count = 0; count < 256; count += 8)
    {
        if(count % 64 == 0)
        {
            freeBlockInBuffer(blk,&buf);
            printf("读入索引块%d\n",count / 64 + 353);
            if ((blk = readBlockFromDisk(count / 64 + 353, &buf)) == NULL)
            {
                perror("Reading Block Failed!\n");
                return -1;
            }
        }
        for(int k = 0; k < 4; k++)
        {
            str[k] = *(blk + count % 64 + k);
        }
        C = atoi(str);
        for(int k = 0; k < 4; k++)
        {
            str[k] = *(blk + count % 64 + 4 + k);
        }
        int addr;
        addr = atoi(str);
        if(C < 128)
        {
            beginaddr = addr;
        }
        else if(C >= 128)
        {
            if(C == 128)
            {
                endaddr = addr;
                find = 1;
            }
            else if(find == 0)
            {
                endaddr = addr - 1;
                find = 1;
            }
        }
    }
    blk_write = getNewBlockInBuffer(&buf);
    //根据索引块找到beginaddr到endaddr即为要搜寻的范围
    current = 0;
    currentGroup = 1;
    for(int num = beginaddr; num <= endaddr; num++)
    {
        freeBlockInBuffer(blk,&buf);
        printf("读入数据块%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(count = 0; count < 56; count += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk + count + k);
            }
            C = atoi(str);
            if(C == 128)
            {
                if(current == 8)
                {
                    current = 0;
                    printf("写入数据块%d\n",120 + currentGroup);
                    if (writeBlockToDisk(blk_write, 120 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    currentGroup++;
                    freeBlockInBuffer(blk_write,&buf);
                    blk_write = getNewBlockInBuffer(&buf);
                }
                for (int k = 0; k < 8; k++)
                {
                    *(blk_write + current * 8 + k) = *(blk + count + k);
                }
                current++;
            }
        }
    }
    printf("写入数据块%d\n",120 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 120 + currentGroup, &buf, current * 8) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }

    printf("(2)读写IO次数:%ld\n", buf.numIO);
    freeBlockInBuffer(blk,&buf);
    /**
    4 结果在401-498
    实现基于排序的连接操作算法（Sort-Merge-Join）：对关系 S 和 R 计算
S.C 连接 R.A ，并统计连接次数，将连接结果存放在磁盘上。 （模拟
实现 select S.C, S.D, R.A, R.B from S inner join R on S.C = R.A）
    */
    printf("********************(4)********************\n");
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }
    unsigned char *blk_A;
    unsigned char *blk_C;
    blk_write = getNewBlockInBuffer(&buf);
    int relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
    {
        if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countA = 0; countA < 56; countA += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_A + countA + k);
            }
            A = atoi(str);
            //读取C
            for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
            {
                if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countC = 0; countC < 56; countC += 8)
                {
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_C + countC + k);
                    }
                    C = atoi(str);
                    if(A == C)
                    {
                        relationCount++;
                        //写S
                        for(int k = 0; k < 8; k++)
                        {
                            *(blk_write + current) = *(blk_C + countC + k);
                            current++;
                        }
                        //写R
                        for(int k = 0; k < 8; k++)
                        {
                            *(blk_write + current) = *(blk_A + countA + k);
                            current++;
                        }
                    }
                    if(current == 64)
                    {
                        current = 0;
                        //将数据块写入
                        printf("写入数据块%d\n",400 + currentGroup);
                        if (writeBlockToDisk(blk_write, 400 + currentGroup, &buf) != 0)
                        {
                            perror("Writing Block Failed!\n");
                            return -1;
                        }
                        blk_write = getNewBlockInBuffer(&buf);
                        currentGroup++;
                    }
                }
                //释放C读取块
                freeBlockInBuffer(blk_C,&buf);
            }
        }
        //释放A读取块
        freeBlockInBuffer(blk_A,&buf);
    }
    //将数据块写入
    printf("写入数据块%d\n",400 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 400 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R和S连接集数量:%d\n",relationCount);
    /**
    5 并集801-841 交集141-142 差集901-927
    实现基于排序或散列的两趟扫描算法，实现并（S R）、交（S R）、
差（S - R）其中一种集合操作算法，将结果存放在磁盘上，并统计并、
交、差操作后的元组个数。
    */
    printf("********************(5)********************\n");
    printf("********************并集********************\n");
    //并集
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    //R
    blk_write = getNewBlockInBuffer(&buf);
    relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
    {
        if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countA = 0; countA < 56; countA += 8)
        {
            relationCount++;
            for(int k = 0; k < 8; k++)
            {
                *(blk_write + current) = *(blk_A + countA + k);
                current++;
            }
            if(current == 64)
            {
                current = 0;
                //将数据块写入
                printf("写入数据块%d\n",800 + currentGroup);
                if (writeBlockToDisk(blk_write, 800 + currentGroup, &buf) != 0)
                {
                    perror("Writing Block Failed!\n");
                    return -1;
                }
                blk_write = getNewBlockInBuffer(&buf);
                currentGroup++;
            }
        }
        //释放A读取块
        freeBlockInBuffer(blk_A,&buf);
    }
    //S
    for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
    {
        if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countC = 0; countC < 56; countC += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + k);
            }
            C = atoi(str);
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + 4 + k);
            }
            D = atoi(str);
            find = 0;
            for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
            {
                if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countA = 0; countA < 56; countA += 8)
                {

                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + k);
                    }
                    A = atoi(str);
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + 4 + k);
                    }
                    B = atoi(str);
                    if(A == C && B == D)
                    {
                        find = 1;
                    }
                }
                //释放A读取块
                freeBlockInBuffer(blk_A,&buf);
            }
            if(find == 0)
            {
                relationCount++;
                for(int k = 0; k < 8; k++)
                {
                    *(blk_write + current) = *(blk_C + countC + k);
                    current++;
                }
                if(current == 64)
                {
                    current = 0;
                    //将数据块写入
                    printf("写入数据块%d\n",800 + currentGroup);
                    if (writeBlockToDisk(blk_write, 800 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    blk_write = getNewBlockInBuffer(&buf);
                    currentGroup++;
                }
            }
        }
        //释放C读取块
        freeBlockInBuffer(blk_C,&buf);
    }
    //将数据块写入
    printf("写入数据块%d\n",800 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 800 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R和S并集数量:%d\n",relationCount);
    printf("********************交集********************\n");
    //交集

    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    blk_write = getNewBlockInBuffer(&buf);
    relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
    {
        if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countC = 0; countC < 56; countC += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + k);
            }
            C = atoi(str);
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + 4 + k);
            }
            D = atoi(str);
            for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
            {
                if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countA = 0; countA < 56; countA += 8)
                {

                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + k);
                    }
                    A = atoi(str);
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + 4 + k);
                    }
                    B = atoi(str);
                    if(A == C && B == D)
                    {
                        relationCount++;
                        for(int k = 0; k < 8; k++)
                        {
                            *(blk_write + current) = *(blk_C + countC + k);
                            current++;
                        }
                        if(current == 64)
                        {
                            current = 0;
                            //将数据块写入
                            printf("写入数据块%d\n",140 + currentGroup);
                            if (writeBlockToDisk(blk_write, 140 + currentGroup, &buf) != 0)
                            {
                                perror("Writing Block Failed!\n");
                                return -1;
                            }
                            blk_write = getNewBlockInBuffer(&buf);
                            currentGroup++;
                        }
                    }
                }
                //释放A读取块
                freeBlockInBuffer(blk_A,&buf);
            }
        }
        //释放C读取块
        freeBlockInBuffer(blk_C,&buf);
    }
    //将数据块写入
    printf("写入数据块%d\n",140 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 140 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R和S交集数量:%d\n",relationCount);
    printf("********************差集********************\n");
    //差集

    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    blk_write = getNewBlockInBuffer(&buf);
    relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
    {
        if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countC = 0; countC < 56; countC += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + k);
            }
            C = atoi(str);
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + 4 + k);
            }
            D = atoi(str);
            find = 0;
            for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
            {
                if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countA = 0; countA < 56; countA += 8)
                {

                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + k);
                    }
                    A = atoi(str);
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + 4 + k);
                    }
                    B = atoi(str);
                    if(A == C && B == D)
                    {
                        find = 1;
                    }
                }
                //释放A读取块
                freeBlockInBuffer(blk_A,&buf);
            }
            if(find == 0)
            {
                relationCount++;
                for(int k = 0; k < 8; k++)
                {
                    *(blk_write + current) = *(blk_C + countC + k);
                    current++;
                }
                if(current == 64)
                {
                    current = 0;
                    //将数据块写入
                    printf("写入数据块%d\n",900 + currentGroup);
                    if (writeBlockToDisk(blk_write, 900 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    blk_write = getNewBlockInBuffer(&buf);
                    currentGroup++;
                }
            }
        }
        //释放C读取块
        freeBlockInBuffer(blk_C,&buf);
    }

    //将数据块写入
    printf("写入数据块%d\n",900 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 900 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R和S差集数量:%d\n",relationCount);
}

